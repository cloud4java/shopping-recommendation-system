package com.querino.ai.controller;

import com.querino.ai.model.Recommendation;
import com.querino.ai.model.RecommendationRequest;
import com.querino.ai.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling shopping recommendation requests.
 */
@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Endpoint for generating personalized shopping recommendations.
     *
     * @param request The recommendation request containing user preferences and purchase history
     * @return A recommendation containing suggested products
     */
    @PostMapping
    public ResponseEntity<Recommendation> getRecommendations(@RequestBody RecommendationRequest request) {
        Recommendation recommendation = recommendationService.generateRecommendations(request);
        return ResponseEntity.ok(recommendation);
    }

    /**
     * Simple health check endpoint.
     *
     * @return A message indicating the service is running
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Shopping Recommendation Service is running!");
    }
}