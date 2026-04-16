package com.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Library Management System
 * - Borrow books, calculate fine based on extra days
 */
public class LibraryManagementSystem {

    // Fine rate per extra day (in rupees)
    private static final double FINE_PER_DAY = 5.0;
    // Allowed borrow period in days
    private static final int ALLOWED_DAYS = 10;

    // User details
    private String userName;
    private String userId;
    private List<String> borrowedBooks;
    private LocalDate issueDate;
    private LocalDate returnDate;

    // Constructor
    public LibraryManagementSystem(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
        this.borrowedBooks = new ArrayList<>();
        this.issueDate = LocalDate.now();
    }

    // Add a book to borrowed list
    public void borrowBook(String bookTitle) {
        borrowedBooks.add(bookTitle);
    }

    // Return all books (set return date)
    public void returnBooks() {
        this.returnDate = LocalDate.now();
    }

    // Calculate fine based on extra days beyond allowed period
    public double calculateFine() {
        if (returnDate == null) {
            return 0.0; // Not returned yet
        }
        long daysBorrowed = ChronoUnit.DAYS.between(issueDate, returnDate);
        long extraDays = daysBorrowed - ALLOWED_DAYS;
        if (extraDays <= 0) {
            return 0.0;
        }
        return extraDays * FINE_PER_DAY;
    }

    // Display full borrowing status
    public String getBorrowingStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Library Borrowing Status =====\n");
        sb.append("User Name: ").append(userName).append("\n");
        sb.append("User ID: ").append(userId).append("\n");
        sb.append("Issue Date: ").append(issueDate).append("\n");
        sb.append("Return Date: ").append(returnDate != null ? returnDate : "Not returned yet").append("\n");
        sb.append("Books Borrowed:\n");
        for (String book : borrowedBooks) {
            sb.append("  - ").append(book).append("\n");
        }
        double fine = calculateFine();
        if (fine > 0) {
            sb.append(String.format("Total Fine: ₹%.2f (Extra days: %d)\n", fine, (int)(fine / FINE_PER_DAY)));
        } else {
            sb.append("No fine (returned on time or not yet returned).\n");
        }
        return sb.toString();
    }

    // Getters for testing
    public String getUserName() { return userName; }
    public String getUserId() { return userId; }
    public List<String> getBorrowedBooks() { return borrowedBooks; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    // Main method – interactive demo
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();

        LibraryManagementSystem lib = new LibraryManagementSystem(name, id);

        System.out.println("Enter books borrowed (type 'done' to finish):");
        while (true) {
            System.out.print("Book title: ");
            String book = scanner.nextLine();
            if (book.equalsIgnoreCase("done")) break;
            lib.borrowBook(book);
        }

        System.out.print("Enter return date (YYYY-MM-DD) or press Enter for today: ");
        String returnDateStr = scanner.nextLine();
        if (!returnDateStr.isEmpty()) {
            lib.returnDate = LocalDate.parse(returnDateStr);
        } else {
            lib.returnBooks();
        }

        System.out.println("\n" + lib.getBorrowingStatus());
        scanner.close();
    }
}