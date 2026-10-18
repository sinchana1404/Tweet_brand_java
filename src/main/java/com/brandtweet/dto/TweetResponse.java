
package com.brandtweet.dto;

import java.util.List;

public class TweetResponse {

    private List<String> brandVoice;
    private String brandVoiceSummary;
    private String brandTone;
    private String toneExplanation;
    private String targetAudience;
    private String communicationStyle;
    private List<String> tweets;

    public TweetResponse() {
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

    public List<String> getTweets() {
        return tweets;
    }

    public void setTweets(List<String> tweets) {
        this.tweets = tweets;
    }
}
