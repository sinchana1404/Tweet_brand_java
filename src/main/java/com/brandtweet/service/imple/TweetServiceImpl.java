
package com.brandtweet.service.imple;

import com.brandtweet.dto.TweetRegenerateRequest;
import com.brandtweet.dto.TweetResponse;
import com.brandtweet.service.TweetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public TweetServiceImpl(
            ChatClient.Builder chatClientBuilder,
            ObjectMapper objectMapper) {

        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public TweetResponse generateTweets(
            String brandName,
            String industry,
            String objective,
            String productDetails) {

        requireText(brandName, "Brand name");
        requireText(industry, "Industry");
        requireText(objective, "Campaign objective");
        requireText(productDetails, "Product details");

        String prompt = """
                You are an expert social media brand strategist.

                Brand name: %s
                Industry: %s
                Campaign objective: %s
                Product details: %s

                Analyze the brand voice and generate exactly 10 unique tweets.

                Return only valid JSON. Do not use Markdown code fences.

                Requirements:
                - Provide 3 or 4 brand voice characteristics.
                - Generate exactly 10 concise tweets for X.
                - Include conversational, promotional, witty,
                  meme-style and informative approaches.
                - Do not invent product claims or statistics.
                - Adapt the tone to the brand and campaign.
                - Do not include unsupported product benefits.

                Return this JSON structure:
                {
                  "brandVoice": ["trait 1", "trait 2", "trait 3"],
                  "brandVoiceSummary": "A concise summary of the brand voice",
                  "brandTone": "The overall tone",
                  "toneExplanation": "Why this tone suits the brand",
                  "targetAudience": "The intended audience",
                  "communicationStyle": "How the brand communicates",
                  "tweets": [
                    "Tweet 1", "Tweet 2", "Tweet 3", "Tweet 4",
                    "Tweet 5", "Tweet 6", "Tweet 7", "Tweet 8",
                    "Tweet 9", "Tweet 10"
                  ]
                }

                Ensure the tweets array has exactly 10 strings
                and brandVoice has 3 or 4 strings.
                """.formatted(
                brandName, industry, objective, productDetails
        );

        String result = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        TweetResponse response = parseResponse(result);

        if (response.getBrandVoice() == null
                || response.getBrandVoice().size() < 3
                || response.getBrandVoice().size() > 4
                || response.getTweets() == null
                || response.getTweets().size() != 10) {

            throw new IllegalStateException(
                    "The AI returned an unexpected response. Please try again."
            );
        }

        if (response.getBrandVoiceSummary() == null
                || response.getBrandTone() == null
                || response.getToneExplanation() == null
                || response.getTargetAudience() == null
                || response.getCommunicationStyle() == null) {

            throw new IllegalStateException(
                    "The AI response is missing required brand analysis fields."
            );
        }

        return response;
    }

    @Override
    public String regenerateTweet(TweetRegenerateRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Regeneration request is required."
            );
        }

        requireText(request.getBrandName(), "Brand name");

        List<String> tweets = request.getExistingTweets();

        if (tweets == null || tweets.size() != 10) {
            throw new IllegalArgumentException(
                    "Exactly 10 existing tweets are required."
            );
        }

        int index = request.getTweetIndex();

        if (index < 0 || index >= tweets.size()) {
            throw new IllegalArgumentException(
                    "Invalid tweet index."
            );
        }

        String prompt = """
                You are a social media brand strategist.

                Brand: %s
                Industry: %s
                Objective: %s
                Product details: %s

                Brand voice: %s
                Brand voice summary: %s
                Brand tone: %s
                Tone explanation: %s
                Target audience: %s
                Communication style: %s

                Existing tweets:
                %s

                Replace only tweet number %d.
                The new tweet must fit the same brand voice,
                avoid repeating the other tweets, and avoid
                unsupported claims.

                Return only the replacement tweet as plain text.
                Do not include a number, explanation, or quotation marks.
                """.formatted(
                safe(request.getBrandName()),
                safe(request.getIndustry()),
                safe(request.getObjective()),
                safe(request.getProductDetails()),
                safe(String.valueOf(request.getBrandVoice())),
                safe(request.getBrandVoiceSummary()),
                safe(request.getBrandTone()),
                safe(request.getToneExplanation()),
                safe(request.getTargetAudience()),
                safe(request.getCommunicationStyle()),
                tweets.toString(),
                index + 1
        );

        String result = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        if (result == null || result.isBlank()) {
            throw new IllegalStateException(
                    "The AI returned an empty tweet. Please try again."
            );
        }

        String replacement = result.trim();

        if (replacement.startsWith("```")) {
            replacement = replacement
                    .replaceFirst("^```[a-zA-Z]*\\s*", "")
                    .replaceFirst("\\s*```$", "")
                    .trim();
        }

        if (replacement.length() >= 2
                && replacement.startsWith("\"")
                && replacement.endsWith("\"")) {
            replacement = replacement.substring(
                    1, replacement.length() - 1
            ).trim();
        }

        if (replacement.isBlank()) {
            throw new IllegalStateException(
                    "The AI returned an empty tweet. Please try again."
            );
        }

        return replacement;
    }

    private TweetResponse parseResponse(String result) {

        if (result == null || result.isBlank()) {
            throw new IllegalStateException(
                    "The AI returned an empty response. Please try again."
            );
        }

        String json = result.trim();

        if (json.startsWith("```")) {
            json = json
                    .replaceFirst("^```[a-zA-Z]*\\s*", "")
                    .replaceFirst("\\s*```$", "")
                    .trim();
        }

        try {
            return objectMapper.readValue(json, TweetResponse.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Could not read the AI response as JSON. Please try again.",
                    e
            );
        }
    }

    private void requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " is required."
            );
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
