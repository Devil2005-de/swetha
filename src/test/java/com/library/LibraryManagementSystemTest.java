package com.library;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LibraryManagementSystemTest {

    private LibraryManagementSystem lib;

    @BeforeEach
    void setUp() {
        lib = new LibraryManagementSystem("Alice", "L101");
        lib.borrowBook("Java Programming");
        lib.borrowBook("Data Structures");
    }

    @Test
    @DisplayName("Fine should be zero when returned within allowed days")
    void testFineWithinAllowedDays() {
        lib.setReturnDate(LocalDate.now().plusDays(5));
        assertEquals(0.0, lib.calculateFine());
    }

    @Test
    @DisplayName("Fine should be zero when returned exactly on due date")
    void testFineOnDueDate() {
        lib.setReturnDate(LocalDate.now().plusDays(10));
        assertEquals(0.0, lib.calculateFine());
    }

    @Test
    @DisplayName("Fine should be calculated correctly for extra days")
    void testFineForExtraDays() {
        lib.setReturnDate(LocalDate.now().plusDays(15)); // 5 extra days
        assertEquals(25.0, lib.calculateFine()); // 5 * 5 = 25
    }

    @Test
    @DisplayName("Fine should be zero if books not returned yet")
    void testFineNotReturned() {
        lib.setReturnDate(null);
        assertEquals(0.0, lib.calculateFine());
    }

    @Test
    @DisplayName("Borrowing status contains user details and books")
    void testBorrowingStatus() {
        lib.returnBooks(); // sets return date to today
        String status = lib.getBorrowingStatus();
        assertTrue(status.contains("Alice"));
        assertTrue(status.contains("L101"));
        assertTrue(status.contains("Java Programming"));
        assertTrue(status.contains("Data Structures"));
    }
}