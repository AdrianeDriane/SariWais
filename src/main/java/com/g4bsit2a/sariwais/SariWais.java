/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.g4bsit2a.sariwais;

/**
 *
 * @author Adriane Dilao
 */
public class SariWais {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        // Preload some accounts
        StoreAccount.preloadAccounts();

        // Test Signup
        StoreAccount.signup("newStore", "password123", "New Store", "789 New Blvd.", "555-555-5555");
        StoreAccount.signup("admin", "admin123", "Duplicate Store", "111 Duplicate Rd.", "111-111-1111"); // Should fail

        // Test Login
        StoreAccount.login("admin", "admin123"); // Should succeed
        StoreAccount.login("admin", "wrongpassword"); // Should fail

        // Test Reset Password
        StoreAccount.resetPassword("staff", "newpassword");
        StoreAccount.login("staff", "newpassword"); // Should succeed

        // Test Logout
        StoreAccount.logout();
    }
}
