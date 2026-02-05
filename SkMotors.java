package com.student.skMotors;

import java.util.Scanner;

public class SkMotors {
    
    // Vehicle class to store vehicle information
    static class Vehicle {
        String registrationNumber;
        double purchaseCost;
        double balanceOwed;
        double totalDeposits;
        double totalExpenses;
        
        Vehicle(String regNum, double cost, double balance) {
            this.registrationNumber = regNum;
            this.purchaseCost = cost;
            this.balanceOwed = balance;
            this.totalDeposits = 0;
            this.totalExpenses = 0;
        }
        
        void recordDeposit(double amount) {
            totalDeposits += amount;
            balanceOwed -= amount;
        }
        
        void recordExpense(double amount) {
            totalExpenses += amount;
        }
        
        double calculateProfitLoss(double sellingPrice) {
            // Profit/Loss = (Total Deposits + Selling Price) - (Purchase Cost + Expenses)
            return (totalDeposits + sellingPrice) - (purchaseCost + totalExpenses);
        }
        
        void displayDetails() {
            System.out.println("=== VEHICLE DETAILS ===");
            System.out.println("Registration No: " + registrationNumber);
            System.out.printf("Purchase Cost: UGX %,.2f%n", purchaseCost);
            System.out.printf("Total Deposits: UGX %,.2f%n", totalDeposits);
            System.out.printf("Total Expenses: UGX %,.2f%n", totalExpenses);
            System.out.printf("Balance Owed: UGX %,.2f%n", balanceOwed);
        }
    }
    
    // Bidder class to store bidder information
    static class Bidder {
        String name;
        double bidAmount;
        boolean hasClearedBalance;
        
        Bidder(String name, double bidAmount) {
            this.name = name;
            this.bidAmount = bidAmount;
            this.hasClearedBalance = false;
        }
        
        void clearBalance(double amount) {
            hasClearedBalance = true;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=========================================");
        System.out.println("       SK. MOTORS AUCTION SYSTEM");
        System.out.println("=========================================");
        
        // Step 1: Enter vehicle details
        System.out.println("=== ENTER VEHICLE DETAILS ===");
        System.out.print("Enter Vehicle Registration Number: ");
        String regNum = scanner.nextLine();
        
        System.out.print("Enter Vehicle Purchase Cost (UGX): ");
        double purchaseCost = scanner.nextDouble();
        
        System.out.print("Enter Balance Owed on Vehicle (UGX): ");
        double balanceOwed = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        
        Vehicle vehicle = new Vehicle(regNum, purchaseCost, balanceOwed);
        
        // Step 2: Record deposits made
        System.out.println(" RECORD DEPOSITS MADE ");
        System.out.print("Enter number of deposits made: ");
        int numDeposits = scanner.nextInt();
        
        for (int i = 0; i < numDeposits; i++) {
            System.out.printf("Enter amount for deposit %d (UGX): ", i + 1);
            double depositAmount = scanner.nextDouble();
            vehicle.recordDeposit(depositAmount);
        }
        
        // Step 3: Record expenses incurred
        System.out.println(" RECORD EXPENSES INCURRED ");
        System.out.print("Enter number of expenses incurred: ");
        int numExpenses = scanner.nextInt();
        
        for (int i = 0; i < numExpenses; i++) {
            System.out.printf("Enter amount for expense %d (UGX): ", i + 1);
            double expenseAmount = scanner.nextDouble();
            vehicle.recordExpense(expenseAmount);
        }
        scanner.nextLine(); // Clear buffer
        
        // Step 4: Accept bids from 3 bidders
        System.out.println(" AUCTION BIDDING ");
        System.out.println("Enter bids from 3 bidders:");
        
        Bidder[] bidders = new Bidder[3];
        Bidder highestBidder = null;
        double highestBid = 0;
        
        for (int i = 0; i < 3; i++) {
            System.out.printf(" Bidder %d ---%n", i + 1);
            System.out.print("Enter bidder name: ");
            String bidderName = scanner.nextLine();
            
            System.out.print("Enter bid amount (UGX): ");
            double bidAmount = scanner.nextDouble();
            scanner.nextLine(); // Clear buffer
            
            bidders[i] = new Bidder(bidderName, bidAmount);
            
            // Track highest bidder
            if (bidAmount > highestBid) {
                highestBid = bidAmount;
                highestBidder = bidders[i];
            }
        }
        
        // Display bidding results
        System.out.println(" BIDDING RESULTS ");
        System.out.println("All Bidders:");
        System.out.println("No.\tBidder Name\tBid Amount (UGX)");
        System.out.println("-----------------------------------");
        for (int i = 0; i < 3; i++) {
            System.out.printf("%d\t%s\t\t%,.2f%n", 
                            i + 1, 
                            bidders[i].name, 
                            bidders[i].bidAmount);
        }
        
        System.out.println(" WINNING BIDDER ");
        System.out.println("Name: " + highestBidder.name);
        System.out.printf("Winning Bid: UGX %,.2f%n", highestBidder.bidAmount);
        
        // Step 5: Check if winning bidder clears balance
        System.out.println(" BALANCE CLEARANCE ");
        vehicle.displayDetails();
        
        System.out.printf("\nWinning bid amount: UGX %,.2f%n", highestBidder.bidAmount);
        System.out.printf("Outstanding balance: UGX %,.2f%n", vehicle.balanceOwed);
        
        if (highestBidder.bidAmount >= vehicle.balanceOwed) {
            System.out.println(" The winning bidder can clear the balance!");
            System.out.print("Has the bidder cleared the balance? (yes/no): ");
            String response = scanner.nextLine().toLowerCase();
            
            if (response.equals("yes") || response.equals("y")) {
                highestBidder.clearBalance(vehicle.balanceOwed);
                vehicle.recordDeposit(vehicle.balanceOwed);
                System.out.println(" Balance cleared successfully!");
            } else {
                System.out.println("️ Balance not cleared yet.");
            }
        } else {
            System.out.println(" The winning bid is insufficient to clear the balance!");
            System.out.printf("Shortfall: UGX %,.2f%n", 
                            vehicle.balanceOwed - highestBidder.bidAmount);
        }
        
        // Step 6: Calculate and display profit/loss
        System.out.println(" PROFIT/LOSS CALCULATION ");
        vehicle.displayDetails();
        
        double profitLoss = vehicle.calculateProfitLoss(highestBidder.bidAmount);
        
        System.out.println(" FINANCIAL SUMMARY ");
        System.out.println("Vehicle: " + vehicle.registrationNumber);
        System.out.println("Winning Bidder: " + highestBidder.name);
        System.out.printf("Winning Bid: UGX %,.2f%n", highestBidder.bidAmount);
        System.out.printf("Total Income (Deposits + Sale): UGX %,.2f%n", 
                         vehicle.totalDeposits + highestBidder.bidAmount);
        System.out.printf("Total Costs (Purchase + Expenses): UGX %,.2f%n", 
                         vehicle.purchaseCost + vehicle.totalExpenses);
        
        System.out.println(" RESULT ");
        if (profitLoss > 0) {
            System.out.printf(" PROFIT MADE: UGX %,.2f%n", profitLoss);
        } else if (profitLoss < 0) {
            System.out.printf(" LOSS INCURRED: UGX %,.2f%n", Math.abs(profitLoss));
        } else {
            System.out.println("️ BREAK EVEN: No profit or loss");
        }
        
        scanner.close();
    }
}

