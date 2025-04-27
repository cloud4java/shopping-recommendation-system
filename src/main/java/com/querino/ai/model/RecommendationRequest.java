package com.querino.ai.model;

import java.util.List;

/**
 * Represents a request for product recommendations.
 */
public class RecommendationRequest {
    private String userId;
    private List<String> interests;
    private List<PurchaseHistory> purchaseHistory;
    private Double budget;
    private List<String> preferredCategories;

    public RecommendationRequest() {
    }

    public RecommendationRequest(String userId, List<String> interests, List<PurchaseHistory> purchaseHistory, 
                                Double budget, List<String> preferredCategories) {
        this.userId = userId;
        this.interests = interests;
        this.purchaseHistory = purchaseHistory;
        this.budget = budget;
        this.preferredCategories = preferredCategories;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<PurchaseHistory> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<PurchaseHistory> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public List<String> getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(List<String> preferredCategories) {
        this.preferredCategories = preferredCategories;
    }

    /**
     * Represents a product that the user has purchased in the past.
     */
    public static class PurchaseHistory {
        private String productId;
        private String productName;
        private String category;
        private double price;
        private String purchaseDate;

        public PurchaseHistory() {
        }

        public PurchaseHistory(String productId, String productName, String category, double price, String purchaseDate) {
            this.productId = productId;
            this.productName = productName;
            this.category = category;
            this.price = price;
            this.purchaseDate = purchaseDate;
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

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
        }
    }
}