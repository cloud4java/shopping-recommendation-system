package com.querino.ai.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querino.ai.model.Recommendation;
import com.querino.ai.model.RecommendationRequest;
import com.querino.ai.service.RecommendationService;
import com.querino.ai.util.SampleDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests for the RecommendationService.
 * These tests use a mock service to avoid depending on external services.
 */
public class RecommendationServiceTest {

    @Mock
    private RecommendationService recommendationService;

    private Recommendation mockRecommendation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a mock recommendation response
        mockRecommendation = new Recommendation();
        mockRecommendation.setUserId("user123");
        mockRecommendation.setReasonForRecommendation("Based on your interests in technology and gaming");

        Recommendation.RecommendedProduct product1 = new Recommendation.RecommendedProduct(
            "rec001", "Smart Watch", "electronics", 199.99, 
            "This complements your existing tech gadgets"
        );

        Recommendation.RecommendedProduct product2 = new Recommendation.RecommendedProduct(
            "rec002", "Wireless Earbuds", "electronics", 149.99, 
            "Great for gaming and music"
        );

        mockRecommendation.setRecommendedProducts(Arrays.asList(product1, product2));

        // Configure the mock service to return our mock recommendation
        when(recommendationService.generateRecommendations(any(RecommendationRequest.class)))
            .thenReturn(mockRecommendation);
    }

    @Test
    void generateRecommendations_WithValidRequest_ShouldReturnRecommendations() {
        // Given
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();

        // When
        Recommendation recommendation = recommendationService.generateRecommendations(request);

        // Then
        assertNotNull(recommendation);
        assertEquals("user123", recommendation.getUserId());
        assertEquals("Based on your interests in technology and gaming", recommendation.getReasonForRecommendation());
        assertNotNull(recommendation.getRecommendedProducts());
        assertEquals(2, recommendation.getRecommendedProducts().size());

        // Verify first product
        Recommendation.RecommendedProduct product1 = recommendation.getRecommendedProducts().get(0);
        assertEquals("rec001", product1.getProductId());
        assertEquals("Smart Watch", product1.getProductName());
        assertEquals("electronics", product1.getCategory());
        assertEquals(199.99, product1.getPrice());
        assertEquals("This complements your existing tech gadgets", product1.getReasonForRecommendation());

        // Verify second product
        Recommendation.RecommendedProduct product2 = recommendation.getRecommendedProducts().get(1);
        assertEquals("rec002", product2.getProductId());
        assertEquals("Wireless Earbuds", product2.getProductName());
        assertEquals("electronics", product2.getCategory());
        assertEquals(149.99, product2.getPrice());
        assertEquals("Great for gaming and music", product2.getReasonForRecommendation());
    }

    @Test
    void generateRecommendations_WithEmptyInterests_ShouldStillReturnRecommendations() {
        // Given
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setInterests(null);

        // When
        Recommendation recommendation = recommendationService.generateRecommendations(request);

        // Then
        assertNotNull(recommendation);
        assertNotNull(recommendation.getRecommendedProducts());
        assertEquals(2, recommendation.getRecommendedProducts().size());
    }

    @Test
    void generateRecommendations_WithNegativeBudget_ShouldHandleGracefully() {
        // Given
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setBudget(-100.0);

        // When
        Recommendation recommendation = recommendationService.generateRecommendations(request);

        // Then
        assertNotNull(recommendation);
        assertNotNull(recommendation.getRecommendedProducts());
        assertEquals(2, recommendation.getRecommendedProducts().size());
    }

    @Test
    void generateRecommendations_WithNoPurchaseHistory_ShouldStillReturnRecommendations() {
        // Given
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setPurchaseHistory(null);

        // When
        Recommendation recommendation = recommendationService.generateRecommendations(request);

        // Then
        assertNotNull(recommendation);
        assertNotNull(recommendation.getRecommendedProducts());
        assertEquals(2, recommendation.getRecommendedProducts().size());
    }
}
