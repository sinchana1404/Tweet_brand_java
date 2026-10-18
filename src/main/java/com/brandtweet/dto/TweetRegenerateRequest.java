
package com.brandtweet.dto;

import java.util.List;

public class TweetRegenerateRequest {

    private String brandName;
    private String industry;
    private String objective;
    private String productDetails;

    private List<String> brandVoice;
    private String brandVoiceSummary;
    private String brandTone;
    private String toneExplanation;
    private String targetAudience;
    private String communicationStyle;

    private List<String> existingTweets;
    private int tweetIndex;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public List<String> getBrandVoice() {
        return brandVoice;
    }

    public void setBrandVoice(List<String> brandVoice) {
        this.brandVoice = brandVoice;
    }

    public String getBrandVoiceSummary() {
        return brandVoiceSummary;
    }

    public void setBrandVoiceSummary(String brandVoiceSummary) {
        this.brandVoiceSummary = brandVoiceSummary;
    }

    public String getBrandTone() {
        return brandTone;
    }

    public void setBrandTone(String brandTone) {
        this.brandTone = brandTone;
    }

    public String getToneExplanation() {
        return toneExplanation;
    }

    public void setToneExplanation(String toneExplanation) {
        this.toneExplanation = toneExplanation;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getCommunicationStyle() {
        return communicationStyle;
    }

    public void setCommunicationStyle(String communicationStyle) {
        this.communicationStyle = communicationStyle;
    }

    public List<String> getExistingTweets() {
        return existingTweets;
    }

    public void setExistingTweets(List<String> existingTweets) {
        this.existingTweets = existingTweets;
    }

    public int getTweetIndex() {
        return tweetIndex;
    }

    public void setTweetIndex(int tweetIndex) {
        this.tweetIndex = tweetIndex;
    }
}
