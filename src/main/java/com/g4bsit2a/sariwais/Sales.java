package com.g4bsit2a.sariwais;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class Sales {
    private final List<Transaction> transactions;
    private StoreAccount account;
    
    public Sales(List<Transaction> transactions, StoreAccount account) {
        this.transactions = transactions;
        this.account = account;
    }

    public List<InventoryItem> getTopSellingProducts(int topN, LocalDate start, LocalDate end) {
        Map<InventoryItem, Integer> salesCount = new HashMap<>();

        for (Transaction transaction : transactions) {
            // Filter transactions based on date range
            LocalDate transactionDate = transaction.getTransactionDate();
            if ((transactionDate.isEqual(start) || transactionDate.isAfter(start)) &&
                (transactionDate.isEqual(end) || transactionDate.isBefore(end))) {
                
                // Iterate over items sold in the transaction
                for (Transaction.TransactionItem item : transaction.getItemsSold()) {
                    salesCount.merge(item.getItem(), item.getQuantity(), Integer::sum);
                }
            }
        }

        // Sort by sales count in descending order and return topN products
        return salesCount.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Method for least selling products
    public List<InventoryItem> getLeastSellingProducts(int topN, LocalDate start, LocalDate end) {
        Map<InventoryItem, Integer> salesCount = new HashMap<>();
        // Checks based on Transactions
        for (Transaction transaction : transactions) {
            // Filter transactions based on date range
            LocalDate transactionDate = transaction.getTransactionDate();
            if ((transactionDate.isEqual(start) || transactionDate.isAfter(start)) &&
                (transactionDate.isEqual(end) || transactionDate.isBefore(end))) {
                
                // Iterate over items sold in the transaction
                for (Transaction.TransactionItem item : transaction.getItemsSold()) {
                    salesCount.merge(item.getItem(), item.getQuantity(), Integer::sum);
                }
            }
        }

        // Sort by sales count in ascending order and return topN products
        return salesCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()) // Sort in ascending order
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public double getTotalRevenue(LocalDate start, LocalDate end) {
        return transactions.stream()
            .filter(t -> {
                LocalDate transactionDate = t.getTransactionDate(); // No need for Instant conversion
                return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
            })
            .mapToDouble(Transaction::getTotalAmount)
            .sum();
    }
    
    public int getTotalTransactions(LocalDate start, LocalDate end) {
        return (int) transactions.stream()
                .filter(t -> {
                    LocalDate transactionDate = t.getTransactionDate();
                    return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
                })
                .count();
    }

    
    // NOTE: We subdivided expenses into Costs of Goods Sold (COGS)
    // and COGP (COGP)
    // COGS refers to the cost of the goods being sold
    // COGP refers to the cost of the total goods being purchased at a specific timeframe
    // - Adriane

    //get Expenses methods
   
    //Cost of Goods Sold
    public double getCOGS(LocalDate start, LocalDate end) { //formerly getTotalCapital
        return transactions.stream()
            .filter(t -> {
                LocalDate transactionDate = t.getTransactionDate(); // No need for Instant conversion
                return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
            })
            .flatMap(t -> t.getItemsSold().stream())
            .mapToDouble(item -> item.getQuantity() * item.getItem().getPurchasePrice())
            .sum();
    }
    
    //Cost of Goods Purchased
    public double getCOGP(LocalDate start, LocalDate end) {
        double sum = 0;
        // Iterate through inventory items
        for (InventoryItem item : account.getInventoryController().viewInventory()) {
            // Check if the item's purchase date falls within the specified range
            LocalDate purchaseDate = item.getPurchaseDate();
            if ((purchaseDate.isEqual(start) || purchaseDate.isAfter(start)) &&
                (purchaseDate.isEqual(end) || purchaseDate.isBefore(end))) {
                sum += (item.getPurchasePrice() * item.getStock());
            }
        }
        return sum + getCOGS(start, end);
    }


    public double getTotalProfit(LocalDate start, LocalDate end) {
        double revenue = getTotalRevenue(start, end);
        double cogs = getCOGS(start, end);
        return revenue - cogs;
    }

    public String generateSalesReport(LocalDate start, LocalDate end) {
        StringBuilder report = new StringBuilder("Sales Report\n");
        report.append("From: ").append(start).append(" To: ").append(end).append("\n");
        report.append("Timestamp: ").append(LocalDate.now()).append("\n");
        report.append("Total Revenue: PHP").append(getTotalRevenue(start, end)).append("\n");
        report.append("Total Profit: PHP").append(getTotalProfit(start, end)).append("\n");
        report.append("Total Transactions: ").append(getTotalTransactions(start, end)).append("\n");
        
        report.append("Top Selling Products:\n");
        for (InventoryItem item : getTopSellingProducts(5, start, end)) {
            report.append("- ").append(item.getProductName()).append("\n");
        }
        report.append("Least Selling Products:\n");
        for (InventoryItem item : getLeastSellingProducts(5, start, end)) {
            report.append("- ").append(item.getProductName()).append("\n");
        }
        
        return report.toString();
    }
    
    public String generateExpensesReport(LocalDate start, LocalDate end){
        StringBuilder report = new StringBuilder("Expenses Report\n");
        report.append("From: ").append(start).append(" To: ").append(end).append("\n");
        report.append("Timestamp: ").append(LocalDate.now()).append("\n");
        report.append("Total COGS: PHP").append(getCOGS(start, end)).append("\n");
        report.append("Total COGP: PHP").append(getCOGP(start, end)).append("\n");
        
        return report.toString();
    }
}
