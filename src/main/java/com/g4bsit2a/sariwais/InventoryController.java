package com.g4bsit2a.sariwais;

import java.util.ArrayList;
import java.util.List;

public class InventoryController {
    private List<InventoryItem> inventory;
    private int itemCounter = 1;

    public InventoryController() {
        inventory = new ArrayList<>();
    }

    //Custom Methods
    
    public void addInventoryItem(InventoryItem item) {
        item.setProductId("P" + itemCounter++); // Assign ID using the store's counter
        inventory.add(item);
    }
    
    public boolean updateStock(String productId, int quantity) {
        InventoryItem item = findInventoryItemById(productId);
        if (item != null) {
            if (quantity >= 0) {
                item.addStock(quantity);
                System.out.println("Added " + quantity + " units to " + item.getProductName() + ".");
            } else {
                boolean success = item.removeStock(-quantity);
                if (success) {
                    System.out.println("Removed " + (-quantity) + " units from " + item.getProductName() + ".");
                } else {
                    System.out.println("Failed to remove " + (-quantity) + " units from " + item.getProductName() + ".");
                }
                return success;
            }
            return true;
        }
        System.out.println("Error: Product with ID " + productId + " not found.");
        return false;
    }


    public boolean deleteInventoryItem(String productId) {
        InventoryItem item = findInventoryItemById(productId);
        if (item != null) {
            inventory.remove(item);
            System.out.println("Deleted product: " + item.getProductName() + " (ID: " + productId + ").");
            return true;
        }
        System.out.println("Error: Product with ID " + productId + " not found.");
        return false;
    }

    
    public List<InventoryItem> viewInventory() {
        return new ArrayList<>(inventory);
    }

    public List<InventoryItem> checkLowStock() {
        List<InventoryItem> lowStockItems = new ArrayList<>();
        for (InventoryItem item : inventory) {
            if (item.isLowStock()) {
                lowStockItems.add(item);
            }
        }
        return lowStockItems;
    }

    // Private Methods
    
    private InventoryItem findInventoryItemById(String productId) {
        return inventory.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}
