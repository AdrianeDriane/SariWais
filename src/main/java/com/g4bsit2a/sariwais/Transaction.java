package com.g4bsit2a.sariwais;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transaction {
    private String transactionId;
    private Date transactionDate;
    private List<TransactionItem> itemsSold;
    private double totalAmount;
    private String customerName;
    private static int transactionCounter = 1;

    public Transaction(String customerName) {
        this.transactionId = generateTransactionId();
        this.transactionDate = new Date();
        this.itemsSold = new ArrayList<>();
        this.totalAmount = 0.0;
        this.customerName = customerName;
    }

    private String generateTransactionId() {
        return "T" + (transactionCounter++);
    }

    // Custom Methods

    public void addItem(InventoryItem item, int quantity) {
        if (item.getStock() >= quantity) {
            itemsSold.add(new TransactionItem(item, quantity));
            item.removeStock(quantity);
            System.out.println("Added " + quantity + " of " + item.getProductName() + " to the transaction.");
        } else {
            System.out.println("Insufficient stock for item: " + item.getProductName());
        }
    }

    public void calculateTotal() {
        totalAmount = 0;
        for (TransactionItem transactionItem : itemsSold) {
            totalAmount += transactionItem.getItem().getPrice() * transactionItem.getQuantity();
        }
    }

    // Getters and setters
    public String getTransactionId() {
        return transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public List<TransactionItem> getItemsSold() {
        return itemsSold;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Nested class for transaction items
    public static class TransactionItem {
        private InventoryItem item;
        private int quantity;

        public TransactionItem(InventoryItem item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public InventoryItem getItem() {
            return item;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
