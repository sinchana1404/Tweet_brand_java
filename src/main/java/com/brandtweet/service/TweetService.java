
package com.brandtweet.service;

import com.brandtweet.dto.TweetRegenerateRequest;
import com.brandtweet.dto.TweetResponse;

public interface TweetService {

    TweetResponse generateTweets(
            String brandName,
            String industry,
            String objective,
            String productDetails
    );

    String regenerateTweet(TweetRegenerateRequest request);
}
