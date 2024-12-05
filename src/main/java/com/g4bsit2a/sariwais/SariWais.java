package com.g4bsit2a.sariwais;

import java.util.Scanner;
import java.time.LocalDate;

public class SariWais {
    public static void main(String[] args) {
        StoreAccount.preloadAccounts();
        Scanner scanner = new Scanner(System.in);
        StoreAccount currentAccount;

        while (true) {
            System.out.println("\n=== SariWais System ===");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Reset Password");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    if (StoreAccount.login(username, password)) {
                        currentAccount = StoreAccount.findByUsername(username);
                        mainMenu(scanner, currentAccount);
                    }
                }
                case 2 -> {
                    System.out.print("Enter new username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter store name: ");
                    String storeName = scanner.nextLine();
                    System.out.print("Enter store address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter contact number: ");
                    String contact = scanner.nextLine();
                    StoreAccount.signup(username, password, storeName, address, contact);
                }
                case 3 -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    StoreAccount.resetPassword(username, newPassword);
                }
                case 4 -> {
                    System.out.println("Exiting SariWais. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void mainMenu(Scanner scanner, StoreAccount account) {
    while (true) {
        System.out.println("\n=== Main Menu for " + account.getStoreName() + " ===");
        System.out.println("1. Inventory Management");
        System.out.println("2. Transactions");
        System.out.println("3. Sales");
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 -> inventoryMenu(scanner, account);
            case 2 -> transactionMenu(scanner, account);
            case 3 -> salesMenu(scanner, account);
            case 4 -> {
                System.out.println("Logged out of " + account.getStoreName() + ".");
                return;
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
    }
}

    private static void salesMenu(Scanner scanner, StoreAccount account) {
        Sales sales = new Sales(account.getTransactions(), account);

        while (true) {
            System.out.println("\n=== Sales Management for " + account.getStoreName() + " ===");
            System.out.println("1. Generate Sales Report");
            System.out.println("2. Generate Expenses Report");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    LocalDate start = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    LocalDate end = LocalDate.parse(scanner.nextLine());
                    System.out.println(sales.generateSalesReport(start, end));
                }
                case 2 -> {
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    LocalDate start = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    LocalDate end = LocalDate.parse(scanner.nextLine());
                    System.out.println(sales.generateExpensesReport(start, end));
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }


    private static void inventoryMenu(Scanner scanner, StoreAccount account) {
        InventoryController inventory = account.getInventoryController();

        while (true) {
            System.out.println("\n=== Inventory Management for " + account.getStoreName() + " ===");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Inventory Item");
            System.out.println("3. Update Stock");
            System.out.println("4. Delete Inventory Item");
            System.out.println("5. Check Low Stock");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("\nInventory:");
                    for (InventoryItem item : inventory.viewInventory()) {
                        System.out.println(item.getProductId() + ": " + item.getProductName() + " - Stock: " +
                                item.getStock() + ", Price: PHP" + item.getPrice());
                    }
                }
                case 2 -> {
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter stock: ");
                    int stock = scanner.nextInt();
                    System.out.print("Enter purchase price: ");
                    double purchasePrice = scanner.nextDouble();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter low stock threshold: ");
                    int threshold = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter category (FOOD, BEVERAGES, HOUSEHOLD, SNACKS, TOILETRIES, OTHER): ");
                    InventoryItem.Category category = InventoryItem.Category.valueOf(scanner.nextLine().toUpperCase());

                    inventory.addInventoryItem(new InventoryItem(null, productName, stock, purchasePrice, price, threshold, LocalDate.now(), category));
                    System.out.println("Item added successfully!");
                }
                case 3 -> {
                    System.out.print("Enter product ID: ");
                    String productId = scanner.nextLine();
                    System.out.print("Enter quantity to add/remove (negative to remove): ");
                    int quantity = scanner.nextInt();
                    inventory.updateStock(productId, quantity);
                }
                case 4 -> {
                    System.out.print("Enter product ID to delete: ");
                    String productId = scanner.nextLine();
                    inventory.deleteInventoryItem(productId);
                }
                case 5 -> {
                    System.out.println("\nLow Stock Items:");
                    for (InventoryItem item : inventory.checkLowStock()) {
                        System.out.println(item.getProductId() + ": " + item.getProductName() + " - Stock: " +
                                item.getStock() + ", Price: PHP" + item.getPrice());
                    }
                }
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void transactionMenu(Scanner scanner, StoreAccount account) {
        while (true) {
            System.out.println("\n=== Transaction Management for " + account.getStoreName() + " ===");
            System.out.println("1. Create Transaction");
            System.out.println("2. View Transactions");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter customer name (optional): ");
                    String customerName = scanner.nextLine();
                    Transaction transaction = new Transaction(customerName);

                    while (true) {
                        System.out.println("\nAdd items to the transaction (Enter 'done' to finish):");
                        System.out.print("Enter product ID: ");
                        String productId = scanner.nextLine();
                        if (productId.equalsIgnoreCase("done")) {
                            break;
                        }

                        InventoryItem item = account.getInventoryController().viewInventory().stream()
                                .filter(i -> i.getProductId().equals(productId))
                                .findFirst()
                                .orElse(null);

                        if (item == null) {
                            System.out.println("Product not found!");
                            continue;
                        }

                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        transaction.addItem(item, quantity);
                    }

                    transaction.calculateTotal();
                    account.addTransaction(transaction);
                    System.out.println("Transaction created successfully. Total: PHP" + transaction.getTotalAmount());
                }
                case 2 -> {
                    System.out.println("\nTransactions:");
                    for (Transaction transaction : account.getTransactions()) {
                        System.out.println("Transaction ID: " + transaction.getTransactionId());
                        System.out.println("Customer: " + transaction.getCustomerName());
                        System.out.println("Date: " + transaction.getTransactionDate());
                        System.out.println("Items:");
                        for (Transaction.TransactionItem item : transaction.getItemsSold()) {
                            System.out.println("- " + item.getItem().getProductName() + " x" + item.getQuantity());
                        }
                        System.out.println("Total: PHP" + transaction.getTotalAmount());
                        System.out.println("---------------");
                    }
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
