package com.querino.ai.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querino.ai.controller.RecommendationController;
import com.querino.ai.model.Recommendation;
import com.querino.ai.model.RecommendationRequest;
import com.querino.ai.service.RecommendationService;
import com.querino.ai.util.SampleDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecommendationIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private RecommendationService recommendationService;

    private Recommendation mockRecommendation;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Initialize ObjectMapper
        objectMapper = new ObjectMapper();

        // Set up the controller with the mock service
        RecommendationController controller = new RecommendationController(recommendationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

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
    void healthCheckEndpoint_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/recommendations/health"))
            .andExpect(status().isOk())
            .andExpect(content().string("Shopping Recommendation Service is running!"));
    }

    @Test
    void getRecommendations_WithValidRequest_ShouldReturnRecommendations() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId", is("user123")))
            .andExpect(jsonPath("$.reasonForRecommendation", is("Based on your interests in technology and gaming")))
            .andExpect(jsonPath("$.recommendedProducts", hasSize(2)))
            .andExpect(jsonPath("$.recommendedProducts[0].productName", is("Smart Watch")))
            .andExpect(jsonPath("$.recommendedProducts[1].productName", is("Wireless Earbuds")));
    }

    @Test
    void getRecommendations_WithEmptyUserId_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setUserId("");

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithNullInterests_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setInterests(null);

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithEmptyInterests_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setInterests(Collections.emptyList());

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithNullPurchaseHistory_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setPurchaseHistory(null);

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithEmptyPurchaseHistory_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setPurchaseHistory(Collections.emptyList());

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithNegativeBudget_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setBudget(-100.0);

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithZeroBudget_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setBudget(0.0);

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithVeryLargeBudget_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setBudget(1000000.0);

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithNullPreferredCategories_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setPreferredCategories(null);

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithEmptyPreferredCategories_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        request.setPreferredCategories(Collections.emptyList());

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithNegativePriceInPurchaseHistory_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        // Modify the first purchase history item to have a negative price
        if (request.getPurchaseHistory() != null && !request.getPurchaseHistory().isEmpty()) {
            RecommendationRequest.PurchaseHistory purchaseItem = request.getPurchaseHistory().get(0);
            purchaseItem.setPrice(-50.0);
        }

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithVeryLongProductName_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();
        // Modify the first purchase history item to have a very long product name
        if (request.getPurchaseHistory() != null && !request.getPurchaseHistory().isEmpty()) {
            RecommendationRequest.PurchaseHistory purchaseItem = request.getPurchaseHistory().get(0);
            StringBuilder longName = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                longName.append("Very Long Product Name ");
            }
            purchaseItem.setProductName(longName.toString());
        }

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void getRecommendations_WithMalformedJson_ShouldReturn400() throws Exception {
        String malformedJson = "{\"userId\":\"user123\", \"interests\": [\"technology\", \"gadgets\", MALFORMED_JSON}";

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getRecommendations_WithExtremelyLargeRequest_ShouldStillWork() throws Exception {
        RecommendationRequest request = SampleDataProvider.createTechEnthusiastRequest();

        // Add a large number of interests
        List<String> manyInterests = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            manyInterests.add("Interest " + i);
        }
        request.setInterests(manyInterests);

        // Add a large number of preferred categories
        List<String> manyCategories = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            manyCategories.add("Category " + i);
        }
        request.setPreferredCategories(manyCategories);

        // Add a large purchase history
        List<RecommendationRequest.PurchaseHistory> largePurchaseHistory = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            largePurchaseHistory.add(new RecommendationRequest.PurchaseHistory(
                "p" + i, 
                "Product " + i, 
                "Category " + (i % 10), 
                i * 10.0, 
                "2023-" + ((i % 12) + 1) + "-" + ((i % 28) + 1)
            ));
        }
        request.setPurchaseHistory(largePurchaseHistory);

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }
}
