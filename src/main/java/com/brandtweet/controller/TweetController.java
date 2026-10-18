
package com.brandtweet.controller;

import com.brandtweet.dto.BrandRequest;
import com.brandtweet.dto.TweetRegenerateRequest;
import com.brandtweet.dto.TweetResponse;
import com.brandtweet.service.TweetService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tweets")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "${FRONTEND_URL:http://localhost:5173}"
})
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/generate")
    public ResponseEntity<TweetResponse> generateTweets(
            @RequestBody BrandRequest request) {

        TweetResponse response = tweetService.generateTweets(
                request.getBrandName(),
                request.getIndustry(),
                request.getObjective(),
                request.getProductDetails()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/regenerate")
    public ResponseEntity<Map<String, String>> regenerateTweet(
            @RequestBody TweetRegenerateRequest request) {

        String tweet = tweetService.regenerateTweet(request);

        return ResponseEntity.ok(Map.of("tweet", tweet));
    }
}
