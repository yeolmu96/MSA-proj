package com.msa.reviewservice.controller;

import com.msa.reviewservice.client.AccountClient;
import com.msa.reviewservice.controller.request.CreateReviewRequest;
import com.msa.reviewservice.controller.request.UpdateReviewRequest;
import com.msa.reviewservice.controller.response.CreateReviewResponse;
import com.msa.reviewservice.controller.response.IdAccountResponse;
import com.msa.reviewservice.controller.response.ReviewAccountInfoResponse;
import com.msa.reviewservice.controller.response.UpdateReviewResponse;
import com.msa.reviewservice.entity.Review;
import com.msa.reviewservice.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AccountClient accountClient;

    @PostMapping("/create")
    public CreateReviewResponse create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateReviewRequest request) {
        String pureToken = extractToken(token);
        IdAccountResponse response = accountClient.getAccountId("Bearer "+pureToken);
        Long accountId = response.getAccountId();

        ReviewAccountInfoResponse requestedAccountInfo = accountClient.getAccountInfo(accountId);
        Review requestedReview = request.toReview(accountId,requestedAccountInfo);
        Review registeredReview = reviewRepository.save(requestedReview);
        return CreateReviewResponse.from(registeredReview);
    }

    private String extractToken(String token) {
        if(token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }



}
