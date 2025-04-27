package com.querino.ai.model;

import java.util.List;

/**
 * Represents a recommendation response from the AI.
 */
public class Recommendation {
    private String userId;
    private List<RecommendedProduct> recommendedProducts;
    private String reasonForRecommendation;

    public Recommendation() {
    }

    public Recommendation(String userId, List<RecommendedProduct> recommendedProducts, String reasonForRecommendation) {
        this.userId = userId;
        this.recommendedProducts = recommendedProducts;
        this.reasonForRecommendation = reasonForRecommendation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<RecommendedProduct> getRecommendedProducts() {
        return recommendedProducts;
    }

    public void setRecommendedProducts(List<RecommendedProduct> recommendedProducts) {
        this.recommendedProducts = recommendedProducts;
    }

    public String getReasonForRecommendation() {
        return reasonForRecommendation;
    }

    public void setReasonForRecommendation(String reasonForRecommendation) {
        this.reasonForRecommendation = reasonForRecommendation;
    }

    /**
     * Represents a product recommendation with a reason.
     */
    public static class RecommendedProduct {
        private String productId;
        private String productName;
        private String category;
        private double price;
        private String reasonForRecommendation;

        public RecommendedProduct() {
        }

        public RecommendedProduct(String productId, String productName, String category, double price, String reasonForRecommendation) {
            this.productId = productId;
            this.productName = productName;
            this.category = category;
            this.price = price;
            this.reasonForRecommendation = reasonForRecommendation;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getReasonForRecommendation() {
            return reasonForRecommendation;
        }

        public void setReasonForRecommendation(String reasonForRecommendation) {
            this.reasonForRecommendation = reasonForRecommendation;
        }
    }
}