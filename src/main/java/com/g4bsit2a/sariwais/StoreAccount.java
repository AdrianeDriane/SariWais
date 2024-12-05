package com.g4bsit2a.sariwais;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class StoreAccount {
    private String username;
    private String password;
    private String storeName;
    private String storeAddress;
    private String contactNumber;
    private final InventoryController inventoryController;
    private final List<Transaction> transactions;
    private Sales sales;


    // Static list
    private final static List<StoreAccount> accountList = new ArrayList<>();

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
        LocalDate date = LocalDate.of(2023, 1, 1);
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Bigas", 100, 38.0, 40.0, 20, date, InventoryItem.Category.FOOD)); // Rice
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Tuyo", 50, 9.0, 10.0, 5, date, InventoryItem.Category.FOOD)); // Dried fish
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Sardinas", 80, 20.0, 25.0, 10, date, InventoryItem.Category.FOOD)); // Canned sardines
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Sabon Panglaba", 60, 10.0, 15.0, 10, date, InventoryItem.Category.TOILETRIES)); // Laundry soap
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Toothpaste", 40, 45.0, 50.0, 5, date, InventoryItem.Category.TOILETRIES));
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Softdrinks", 100, 18.0, 20.0, 10, date,InventoryItem.Category.BEVERAGES)); // Soda
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Kape", 75, 10.0, 12.0, 10, date, InventoryItem.Category.BEVERAGES)); // Coffee
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Chicharon", 30, 20.0, 30.0, 5, date, InventoryItem.Category.SNACKS)); // Pork cracklings
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Yakult", 50, 6.0, 8.0, 10, date, InventoryItem.Category.BEVERAGES)); // Probiotic drink
        admin.getInventoryController().addInventoryItem(new InventoryItem(null, "Cooking Oil", 20, 55.5, 70.0, 5, date, InventoryItem.Category.HOUSEHOLD)); // Cooking oil
        // Transaction 1
        LocalDate date1 = LocalDate.of(2023,01,05);
        Transaction transaction1 = new Transaction("Juan Dela Cruz", date1);
        transaction1.addItem(admin.getInventoryController().viewInventory().get(0), 5); // 5 Bigas
        transaction1.addItem(admin.getInventoryController().viewInventory().get(1), 10); // 10 Tuyo
        transaction1.calculateTotal();
        admin.addTransaction(transaction1);
        // Transaction 2
        LocalDate date2 = LocalDate.of(2023,03,15);
        Transaction transaction2 = new Transaction("Maria Clara", date2);
        transaction2.addItem(admin.getInventoryController().viewInventory().get(2), 3); // 3 Sardinas
        transaction2.addItem(admin.getInventoryController().viewInventory().get(5), 2); // 2 Softdrinks
        transaction2.calculateTotal();
        admin.addTransaction(transaction2);
        // Transaction 3
        LocalDate date3 = LocalDate.of(2023,06,10);
        Transaction transaction3 = new Transaction("Jose Rizal", date3);
        transaction3.addItem(admin.getInventoryController().viewInventory().get(7), 4); // 4 Chicharon
        transaction3.addItem(admin.getInventoryController().viewInventory().get(9), 1); // 1 Cooking Oil
        transaction3.calculateTotal();
        admin.addTransaction(transaction3);

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
    
    public Sales getSales() {
        return sales;
    }
}
