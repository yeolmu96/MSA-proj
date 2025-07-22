package com.msa.reviewservice.controller;

import com.msa.reviewservice.client.AccountClient;
import com.msa.reviewservice.client.InfoClient;
import com.msa.reviewservice.controller.request.CreateReviewRequest;
import com.msa.reviewservice.controller.request.UpdateReviewRequest;
import com.msa.reviewservice.controller.response.*;
import com.msa.reviewservice.entity.Review;
import com.msa.reviewservice.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private InfoClient infoClient;

    @PostMapping("/create")
    public CreateReviewResponse create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateReviewRequest request) {
        String pureToken = extractToken(token);
        IdAccountResponse response = accountClient.getAccountId("Bearer "+pureToken);
        Long accountId = response.getAccountId();

        ReviewAccountInfoResponse requestedAccountInfo = accountClient.getAccountInfo(accountId);
        Long trainingId = requestedAccountInfo.getTrainingId();
        ApiResponse<TrainingResponse> training = infoClient.getTrainingById(trainingId);
        String trainingName = training.getData().getName();
        int trainingPeriod = training.getData().getPeriod();
        NcsType ncsType = training.getData().getNcsType();
        String imagePath = "training"+trainingId+".png";

        Review requestedReview = request.toReview(accountId,requestedAccountInfo.getNickname(), trainingId, trainingName, trainingPeriod, ncsType, imagePath);
        Review registeredReview = reviewRepository.save(requestedReview);
        return CreateReviewResponse.from(registeredReview);
    }

    @GetMapping("/list")
    public List<Review> list() {
        return reviewRepository.findAll();
    }

//    @GetMapping("/list")
//    public List<ReviewListResponse> list() {
//        List<Review> all = reviewRepository.findAll();
//        List<ReviewListResponse> res = new ArrayList<>();
//        all.stream().forEach(r -> {
//            ReviewListResponse reviewListResponse = new ReviewListResponse(r.getRating(), r.getTitle());
//            res.add(reviewListResponse);
//        });
//        return res;
//    }

    @GetMapping("/list-by/{trainingName}")
    public List<Review> listByTrainingId(@PathVariable String trainingName) {
        return reviewRepository.findByTrainingName(trainingName);
    }

    @PostMapping("/list-by/{userId}")
    public List<Review> listbyuser(@RequestHeader("Authorization") String token, @PathVariable Long userId) {
        String pureToken = extractToken(token);
        IdAccountResponse response = accountClient.getAccountId("Bearer "+pureToken);
        Long accountId = response.getAccountId();

        if(userId != accountId) {
            throw new RuntimeException("사용자 외에는 조회할 수 없습니다.");
        }

        return reviewRepository.findByAccountId(userId);
    }

    @PostMapping("/update")
    public UpdateReviewResponse update(@RequestHeader("Authorization") String token, @RequestBody UpdateReviewRequest request) {
        log.info("Update review request: {}", request);

        String pureToken = extractToken(token);
        IdAccountResponse response = accountClient.getAccountId("Bearer "+pureToken);
        Long accountId = response.getAccountId();

        Long requestedReviewId = request.getReviewId();
        Review foundReview = reviewRepository.findById(requestedReviewId).orElseThrow(() -> new RuntimeException("존재하지 않는 리뷰입니다."));

        if(!foundReview.getId().equals(requestedReviewId)) {
            throw new RuntimeException("리뷰를 등록한 사람이 아닙니다.");
        }

        foundReview.setTitle(request.getTitle());
        foundReview.setContent(request.getContent());
        foundReview.setRating(request.getRating());

        Review updatedReview = reviewRepository.save(foundReview);
        return UpdateReviewResponse.from(updatedReview);
    }

    @PostMapping("/delete")
    public void delete(@RequestHeader("Authorization") String token, @RequestParam Long id) {
        log.info("Delete review request: {}", id);

        String pureToken = extractToken(token);
        IdAccountResponse response = accountClient.getAccountId("Bearer "+pureToken);
        Long accountId = response.getAccountId();

        Review foundReview = reviewRepository.findById(id).orElseThrow(()->new RuntimeException("존재하지 않는 리뷰입니다."));
        if(!foundReview.getId().equals(id)) {
            throw new RuntimeException("리뷰를 등록한 사람이 아닙니다..");
        }
        reviewRepository.deleteById(id);
    }

    private String extractToken(String token) {
        if(token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }



}
