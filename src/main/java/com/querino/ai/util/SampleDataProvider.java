package com.querino.ai.util;

import com.querino.ai.model.RecommendationRequest;
import com.querino.ai.model.RecommendationRequest.PurchaseHistory;

import java.util.Arrays;
import java.util.List;

/**
 * Provides sample data for testing the recommendation system.
 */
public class SampleDataProvider {

    /**
     * Creates a sample recommendation request for a tech enthusiast.
     *
     * @return A sample recommendation request
     */
    public static RecommendationRequest createTechEnthusiastRequest() {
        RecommendationRequest request = new RecommendationRequest();
        request.setUserId("user123");
        request.setInterests(Arrays.asList("technology", "gadgets", "programming", "gaming"));
        request.setBudget(500.0);
        request.setPreferredCategories(Arrays.asList("electronics", "computers", "accessories"));
        
        List<PurchaseHistory> purchaseHistory = Arrays.asList(
            new PurchaseHistory("p001", "Wireless Headphones", "electronics", 129.99, "2023-10-15"),
            new PurchaseHistory("p002", "Mechanical Keyboard", "computers", 89.99, "2023-11-20"),
            new PurchaseHistory("p003", "Gaming Mouse", "accessories", 59.99, "2023-12-05")
        );
        
        request.setPurchaseHistory(purchaseHistory);
        return request;
    }

    /**
     * Creates a sample recommendation request for a fitness enthusiast.
     *
     * @return A sample recommendation request
     */
    public static RecommendationRequest createFitnessEnthusiastRequest() {
        RecommendationRequest request = new RecommendationRequest();
        request.setUserId("user456");
        request.setInterests(Arrays.asList("fitness", "running", "nutrition", "outdoor activities"));
        request.setBudget(300.0);
        request.setPreferredCategories(Arrays.asList("sports equipment", "fitness trackers", "athletic wear"));
        
        List<PurchaseHistory> purchaseHistory = Arrays.asList(
            new PurchaseHistory("p101", "Running Shoes", "athletic wear", 119.99, "2023-09-10"),
            new PurchaseHistory("p102", "Fitness Tracker", "fitness trackers", 149.99, "2023-10-25"),
            new PurchaseHistory("p103", "Yoga Mat", "sports equipment", 29.99, "2023-11-15")
        );
        
        request.setPurchaseHistory(purchaseHistory);
        return request;
    }

    /**
     * Creates a sample recommendation request for a home cook.
     *
     * @return A sample recommendation request
     */
    public static RecommendationRequest createHomeCookRequest() {
        RecommendationRequest request = new RecommendationRequest();
        request.setUserId("user789");
        request.setInterests(Arrays.asList("cooking", "baking", "kitchen gadgets", "gourmet food"));
        request.setBudget(200.0);
        request.setPreferredCategories(Arrays.asList("kitchen appliances", "cookware", "bakeware"));
        
        List<PurchaseHistory> purchaseHistory = Arrays.asList(
            new PurchaseHistory("p201", "Stand Mixer", "kitchen appliances", 249.99, "2023-08-05"),
            new PurchaseHistory("p202", "Non-stick Cookware Set", "cookware", 129.99, "2023-09-20"),
            new PurchaseHistory("p203", "Digital Kitchen Scale", "kitchen gadgets", 24.99, "2023-11-30")
        );
        
        request.setPurchaseHistory(purchaseHistory);
        return request;
    }
}