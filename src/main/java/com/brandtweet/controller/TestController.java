
package com.brandtweet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/health")
    public String checkHealth() {
        return "AI Brand Tweet Generator Backend is running!";
    }
}
