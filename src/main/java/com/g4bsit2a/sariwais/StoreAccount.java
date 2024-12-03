package com.g4bsit2a.sariwais;

import java.util.ArrayList;
import java.util.List;

public class StoreAccount {
    private String username;
    private String password;
    private String storeName;
    private String storeAddress;
    private String contactNumber;
    private InventoryController inventoryController;
    private List<Transaction> transactions;


    // Static list
    private static List<StoreAccount> accountList = new ArrayList<>();

    public StoreAccount(String username, String password, String storeName, String storeAddress, String contactNumber) {
        this.username = username;
        this.password = password;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.contactNumber = contactNumber;
        this.inventoryController = new InventoryController();
        this.transactions = new ArrayList<>();
    }
    
    public InventoryController getInventoryController() {
        return inventoryController;
    }

    // Custom Methods
    
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        System.out.println("Transaction " + transaction.getTransactionId() + " added successfully.");
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public static StoreAccount findByUsername(String username) {
        return findAccountByUsername(username);
    }
    
    public static boolean login(String username, String password) {
        StoreAccount account = findAccountByUsername(username);
        if (account != null && isPasswordCorrect(account, password)) {
            System.out.println("Login successful for store: " + account.getStoreName());
            return true;
        }
        System.out.println("Login failed. Invalid credentials.");
        return false;
    }

    public static void logout() {
        System.out.println("Account logged out.");
        // Assignment: Logic for logout
    }

    public static boolean signup(String username, String password, String storeName, String storeAddress, String contactNumber) {
        if (isUsernameTaken(username)) {
            System.out.println("Signup failed. Username already exists.");
            return false;
        }
        addNewAccount(username, password, storeName, storeAddress, contactNumber);
        return true;
    }

    public static boolean resetPassword(String username, String newPassword) {
        StoreAccount account = findAccountByUsername(username);
        if (account != null) {
            updateAccountPassword(account, newPassword);
            return true;
        }
        System.out.println("Password reset failed. Account not found.");
        return false;
    }

    
    // Private Methods
    
    private static StoreAccount findAccountByUsername(String username) {
        return accountList.stream()
                .filter(account -> account.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    private static boolean isPasswordCorrect(StoreAccount account, String password) {
        return account.getPassword().equals(password);
    }

    private static boolean isUsernameTaken(String username) {
        return findAccountByUsername(username) != null;
    }

    private static void addNewAccount(String username, String password, String storeName, String storeAddress, String contactNumber) {
        StoreAccount newAccount = new StoreAccount(username, password, storeName, storeAddress, contactNumber);
        accountList.add(newAccount);
        System.out.println("Signup successful for store: " + storeName);
    }

    private static void updateAccountPassword(StoreAccount account, String newPassword) {
        account.setPassword(newPassword);
        System.out.println("Password reset successful for store: " + account.getStoreName());
    }
    
    // Hardcoded data
    public static void preloadAccounts() {
        StoreAccount admin = new StoreAccount("admin", "admin123", "Admin Store", "123 Admin St.", "123-456-7890");

        // Adding Filipino products to inventory
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Bigas", 100, 40.0, 20, InventoryItem.Category.FOOD)); // Rice
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Tuyo", 50, 10.0, 5, InventoryItem.Category.FOOD)); // Dried fish
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Sardinas", 80, 25.0, 10, InventoryItem.Category.FOOD)); // Canned sardines
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Sabon Panglaba", 60, 15.0, 10, InventoryItem.Category.TOILETRIES)); // Laundry soap
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Toothpaste", 40, 50.0, 5, InventoryItem.Category.TOILETRIES));
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Softdrinks", 100, 20.0, 10, InventoryItem.Category.BEVERAGES)); // Soda
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Kape", 75, 12.0, 10, InventoryItem.Category.BEVERAGES)); // Coffee
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Chicharon", 30, 30.0, 5, InventoryItem.Category.SNACKS)); // Pork cracklings
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Yakult", 50, 8.0, 10, InventoryItem.Category.BEVERAGES)); // Probiotic drink
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Cooking Oil", 20, 70.0, 5, InventoryItem.Category.HOUSEHOLD)); // Cooking oil

        // Adding preloaded transactions
        Transaction transaction1 = new Transaction("Juan Dela Cruz");
        transaction1.addItem(admin.getInventoryController().viewInventory().get(0), 5); // Bigas
        transaction1.addItem(admin.getInventoryController().viewInventory().get(2), 3); // Sardinas
        transaction1.calculateTotal();

        Transaction transaction2 = new Transaction("Maria Clara");
        transaction2.addItem(admin.getInventoryController().viewInventory().get(3), 2); // Sabon Panglaba
        transaction2.addItem(admin.getInventoryController().viewInventory().get(7), 5); // Chicharon
        transaction2.calculateTotal();

        admin.addTransaction(transaction1);
        admin.addTransaction(transaction2);

        accountList.add(admin);
    }

    
    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
