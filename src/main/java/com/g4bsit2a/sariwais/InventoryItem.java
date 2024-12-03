package com.g4bsit2a.sariwais;

public class InventoryItem {
    public enum Category {
        FOOD, BEVERAGES, HOUSEHOLD, SNACKS, TOILETRIES, OTHER
    }


    private String productId;
    private String productName;
    private int stock;
    private double price;
    private int lowStockThreshold;
    private Category category;

    public InventoryItem(String productId, String productName, int stock, double price, int lowStockThreshold, Category category) {
    this.productId = productId;
    this.productName = productName;
    this.stock = stock;
    this.price = price;
    this.lowStockThreshold = lowStockThreshold;
    this.category = category;
}
    
    // Custom Methods
    public void addStock(int quantity) {
        stock += quantity;
    }

    public boolean removeStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            return true;
        }
        System.out.println("Error: Insufficient stock to remove " + quantity + " units of " + productName + ".");
        return false;
    }

    public boolean isLowStock() {
        return stock < lowStockThreshold;
    }
    
    //Private methods

    // Getters and setters
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(int lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
