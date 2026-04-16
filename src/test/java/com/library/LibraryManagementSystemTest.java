package com.library;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

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
        // Issue date is today (set in constructor). Return 5 days later.
        lib.returnDate = LocalDate.now().plusDays(5);
        assertEquals(0.0, lib.calculateFine());
    }

    @Test
    @DisplayName("Fine should be zero when returned exactly on due date")
    void testFineOnDueDate() {
        lib.returnDate = LocalDate.now().plusDays(10);
        assertEquals(0.0, lib.calculateFine());
    }

    @Test
    @DisplayName("Fine should be calculated correctly for extra days")
    void testFineForExtraDays() {
        lib.returnDate = LocalDate.now().plusDays(15); // 5 extra days
        assertEquals(25.0, lib.calculateFine()); // 5 * 5 = 25
    }

    @Test
    @DisplayName("Fine should be zero if books not returned yet")
    void testFineNotReturned() {
        lib.returnDate = null;
        assertEquals(0.0, lib.calculateFine());
    }

    @Test
    @DisplayName("Borrowing status contains user details and books")
    void testBorrowingStatus() {
        lib.returnBooks();
        String status = lib.getBorrowingStatus();
        assertTrue(status.contains("Alice"));
        assertTrue(status.contains("L101"));
        assertTrue(status.contains("Java Programming"));
        assertTrue(status.contains("Data Structures"));
    }
}