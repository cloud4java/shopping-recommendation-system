package com.querino.ai;

import com.querino.ai.model.Recommendation;
import com.querino.ai.service.RecommendationService;
import com.querino.ai.util.SampleDataProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main application class for the Shopping Recommendation System.
 */
@SpringBootApplication
public class AiRecommendationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiRecommendationDemoApplication.class, args);
    }

    /**
     * CommandLineRunner to demonstrate the shopping recommendation functionality.
     * 
     * @param recommendationService The service for generating shopping recommendations
     * @return A CommandLineRunner that demonstrates the functionality
     */
    @Bean
    CommandLineRunner runner(RecommendationService recommendationService) {
        return args -> {
            System.out.println("=== Shopping Recommendation System Demo ===");

            // Generate recommendations for a tech enthusiast
            System.out.println("\n--- Tech Enthusiast Recommendations ---");
            Recommendation techRecommendation = 
                recommendationService.generateRecommendations(SampleDataProvider.createTechEnthusiastRequest());
            printRecommendation(techRecommendation);

            // Generate recommendations for a fitness enthusiast
            System.out.println("\n--- Fitness Enthusiast Recommendations ---");
            Recommendation fitnessRecommendation = 
                recommendationService.generateRecommendations(SampleDataProvider.createFitnessEnthusiastRequest());
            printRecommendation(fitnessRecommendation);

            // Generate recommendations for a home cook
            System.out.println("\n--- Home Cook Recommendations ---");
            Recommendation cookRecommendation = 
                recommendationService.generateRecommendations(SampleDataProvider.createHomeCookRequest());
            printRecommendation(cookRecommendation);

            System.out.println("\n=== Demo Complete ===");
            System.out.println("The REST API is now available at http://localhost:8080/api/recommendations");
        };
    }

    /**
     * Helper method to print recommendation details.
     * 
     * @param recommendation The recommendation to print
     */
    private void printRecommendation(Recommendation recommendation) {
        System.out.println("User ID: " + recommendation.getUserId());
        System.out.println("Overall Reason: " + recommendation.getReasonForRecommendation());
        System.out.println("Recommended Products:");

        if (recommendation.getRecommendedProducts() != null) {
            recommendation.getRecommendedProducts().forEach(product -> {
                System.out.println("  - " + product.getProductName() + " (" + product.getCategory() + ") - $" + product.getPrice());
                System.out.println("    Reason: " + product.getReasonForRecommendation());
            });
        } else {
            System.out.println("  No products recommended.");
        }
    }
}