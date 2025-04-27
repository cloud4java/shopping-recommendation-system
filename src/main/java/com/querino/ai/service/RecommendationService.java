package com.querino.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querino.ai.model.Recommendation;
import com.querino.ai.model.RecommendationRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for generating shopping recommendations using OpenAI.
 */
@Service
public class RecommendationService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public RecommendationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Generates personalized shopping recommendations based on user preferences and purchase history.
     *
     * @param request The recommendation request containing user preferences and purchase history
     * @return A recommendation containing suggested products
     */
    public Recommendation generateRecommendations(RecommendationRequest request) {
        try {
            String requestJson = objectMapper.writeValueAsString(request);

            // Create a prompt template for generating recommendations
            String templateString = """
                You are a shopping recommendation assistant. Based on the user's preferences and purchase history,
                recommend products that they might be interested in.

                User information:
                ```
                {requestJson}
                ```

                Provide recommendations in the following JSON format:
                {
                  "userId": "user's ID",
                  "recommendedProducts": [
                    {
                      "productId": "generated unique ID",
                      "productName": "name of the product",
                      "category": "product category",
                      "price": price as a number,
                      "reasonForRecommendation": "detailed reason why this product is recommended"
                    }
                  ],
                  "reasonForRecommendation": "overall explanation for the recommendations"
                }

                Generate 3-5 product recommendations that match the user's interests, are within their budget (if specified),
                and align with their preferred categories (if specified). Consider their purchase history to avoid recommending
                similar products they already own. Each recommendation should have a unique ID, realistic name, appropriate category,
                reasonable price, and a personalized reason for recommendation.

                Respond ONLY with the JSON, no additional text.
            """;

            Map<String, Object> variables = new HashMap<>();
            variables.put("requestJson", requestJson);

            PromptTemplate promptTemplate = new PromptTemplate(templateString, variables);
            Prompt prompt = promptTemplate.create();

            // Call OpenAI to generate recommendations
            String responseContent = chatClient.prompt(prompt).call().content();

            // Parse the JSON response into a Recommendation object
            return objectMapper.readValue(responseContent, Recommendation.class);
        } catch (JsonProcessingException e) {
            // In case of JSON processing error, return a basic recommendation with an error message
            Recommendation errorRecommendation = new Recommendation();
            errorRecommendation.setUserId(request.getUserId());
            errorRecommendation.setRecommendedProducts(new ArrayList<>());
            errorRecommendation.setReasonForRecommendation("Error generating recommendations: " + e.getMessage());
            return errorRecommendation;
        }
    }
}
