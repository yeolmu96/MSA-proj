package com.msa.gathering.service;

import com.msa.gathering.controller.request.CommentRequest;
import com.msa.gathering.controller.response.CommentResponse;
import com.msa.gathering.entity.Comment;
import com.msa.gathering.entity.Gathering;
import com.msa.gathering.entity.GatheringMember;
import com.msa.gathering.repository.CommentRepository;
import com.msa.gathering.repository.GatheringMemberRepository;
import com.msa.gathering.repository.GatheringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public CommentResponse addComment(CommentRequest request) {
        try {
            // 모임 조회
            Gathering gathering = gatheringRepository.findById(request.getGatheringId())
                    .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."));
            
            // 사용자가 모임의 멤버인지 확인
            boolean isMember = gatheringMemberRepository.findByGatheringIdAndAccountId(
                    request.getGatheringId(), request.getAccountId()).isPresent();
            
            if (!isMember) {
                throw new RuntimeException("모임의 멤버만 댓글을 작성할 수 있습니다.");
            }
            
            // 댓글 생성 및 저장
            Comment comment = Comment.builder()
                    .gathering(gathering)
                    .accountId(request.getAccountId())
                    .content(request.getContent())
                    .build();
            
            Comment savedComment = commentRepository.save(comment);
            
            // 호스트에게 알림 전송 (작성자가 호스트가 아닌 경우에만)
            if (!request.getAccountId().equals(gathering.getHostId())) {
                notificationService.createNotification(
                        gathering.getHostId(),
                        "새로운 댓글이 작성되었습니다.",
                        "모임 '" + gathering.getTitle() + "'에 새로운 댓글이 작성되었습니다.",
                        "/gathering/" + gathering.getId()
                );
            }
            
            return CommentResponse.from(savedComment);
        } catch (Exception e) {
            log.error("댓글 작성 중 오류 발생", e);
            throw new RuntimeException("댓글 작성 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByGathering(Long gatheringId) {
        try {
            // 모임 존재 여부 확인
            if (!gatheringRepository.existsById(gatheringId)) {
                throw new RuntimeException("모임을 찾을 수 없습니다.");
            }
            
            // 모임의 모든 댓글 조회
            List<Comment> comments = commentRepository.findByGatheringId(gatheringId);
            
            // 응답 객체로 변환
            return comments.stream()
                    .map(CommentResponse::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("댓글 조회 중 오류 발생", e);
            throw new RuntimeException("댓글 조회 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long commentId, Long accountId, String content) {
        try {
            // 댓글 조회
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
            
            // 작성자 확인
            if (!comment.getAccountId().equals(accountId)) {
                throw new RuntimeException("자신이 작성한 댓글만 수정할 수 있습니다.");
            }
            
            // 댓글 내용 업데이트
            comment.updateContent(content);
            Comment updatedComment = commentRepository.save(comment);
            
            return CommentResponse.from(updatedComment);
        } catch (Exception e) {
            log.error("댓글 수정 중 오류 발생", e);
            throw new RuntimeException("댓글 수정 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    @Transactional
    public boolean deleteComment(Long commentId, Long accountId) {
        try {
            // 댓글 조회
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
            
            // 작성자 또는 모임 호스트인지 확인
            boolean isAuthor = comment.getAccountId().equals(accountId);
            boolean isHost = comment.getGathering().getHostId().equals(accountId);
            
            if (!isAuthor && !isHost) {
                throw new RuntimeException("자신이 작성한 댓글이나 모임의 호스트만 댓글을 삭제할 수 있습니다.");
            }
            
            // 댓글 삭제
            commentRepository.deleteById(commentId);
            return true;
        } catch (Exception e) {
            log.error("댓글 삭제 중 오류 발생", e);
            return false;
        }
    }
}
