package com.g4bsit2a.sariwais;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class Sales {
    private final List<Transaction> transactions;
    
    public Sales(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<InventoryItem> getTopSellingProducts(int topN) {
        Map<InventoryItem, Integer> salesCount = new HashMap<>();
        for (Transaction transaction : transactions) {
            for (Transaction.TransactionItem item : transaction.getItemsSold()) {
                salesCount.merge(item.getItem(), item.getQuantity(), Integer::sum);
            }
        }
        return salesCount.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public double getTotalRevenue(LocalDate start, LocalDate end) {
        return transactions.stream()
                .filter(t -> {
                    LocalDate transactionDate = t.getTransactionDate().toInstant()
                                                 .atZone(ZoneId.systemDefault())
                                                 .toLocalDate();
                    return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
                })
                .mapToDouble(Transaction::getTotalAmount)
                .sum();
    }

    public double getTotalCapital(LocalDate start, LocalDate end) {
        return transactions.stream()
                .filter(t -> {
                    LocalDate transactionDate = t.getTransactionDate().toInstant()
                                                 .atZone(ZoneId.systemDefault())
                                                 .toLocalDate();
                    return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
                })
                .flatMap(t -> t.getItemsSold().stream())
                .mapToDouble(item -> item.getItem().getStock() * item.getItem().getPrice())
                .sum();
    }

    public double getTotalProfit(LocalDate start, LocalDate end) {
        double revenue = getTotalRevenue(start, end);
        double capital = getTotalCapital(start, end);
        return revenue - capital;
    }

    public String generateSalesReport(LocalDate start, LocalDate end) {
        StringBuilder report = new StringBuilder("Sales Report\n");
        report.append("From: ").append(start).append(" To: ").append(end).append("\n");
        report.append("Total Revenue: PHP").append(getTotalRevenue(start, end)).append("\n");
        report.append("Total Profit: PHP").append(getTotalProfit(start, end)).append("\n");
        report.append("Top Selling Products:\n");
        for (InventoryItem item : getTopSellingProducts(5)) {
            report.append("- ").append(item.getProductName()).append("\n");
        }
        return report.toString();
    }
}
