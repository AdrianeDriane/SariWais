package com.g4bsit2a.sariwais;

import java.util.ArrayList;
import java.util.List;

public class StoreAccount {
    private String username;
    private String password;
    private String storeName;
    private String storeAddress;
    private String contactNumber;

    // Static list
    private static List<StoreAccount> accountList = new ArrayList<>();

    public StoreAccount(String username, String password, String storeName, String storeAddress, String contactNumber) {
        this.username = username;
        this.password = password;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.contactNumber = contactNumber;
    }

    // Custom Methods
    
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
        // TODO: Logic for logout
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

    // Preload accounts for testing
    public static void preloadAccounts() {
        accountList.add(new StoreAccount("admin", "admin123", "Admin Store", "123 Admin St.", "123-456-7890"));
        accountList.add(new StoreAccount("staff", "staff123", "Staff Store", "456 Staff Ave.", "987-654-3210"));
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
