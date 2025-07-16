package com.msa.gathering.repository;

import com.msa.gathering.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByGatheringId(Long gatheringId);
    
    void deleteByGatheringId(Long gatheringId);
}
