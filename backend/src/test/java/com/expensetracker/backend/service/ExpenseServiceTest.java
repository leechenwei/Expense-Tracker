package com.expensetracker.backend.service;

import com.expensetracker.backend.model.Expense;
import com.expensetracker.backend.model.User;
import com.expensetracker.backend.repository.ExpenseRepository;
import com.expensetracker.backend.repository.UserRepository;
import com.expensetracker.backend.service.ExpenseService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private User testUser;
    private Expense testExpense;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");

        testExpense = new Expense();
        testExpense.setId(1L);
        testExpense.setDescription("Lunch");
        testExpense.setAmount(new BigDecimal("15.00"));
        testExpense.setCategory("Food");
        testExpense.setDate(LocalDate.now());
        testExpense.setUser(testUser);
    }

    @Test
    void testGetAllExpensesForUser() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(expenseRepository.findByUserId(1L)).thenReturn(Collections.singletonList(testExpense));

        // When
        List<Expense> expenses = expenseService.getAllExpensesForUser("test@example.com");

        // Then
        assertNotNull(expenses);
        assertEquals(1, expenses.size());
        assertEquals("Lunch", expenses.get(0).getDescription());
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(expenseRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testAddExpense() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(expenseRepository.save(any(Expense.class))).thenReturn(testExpense);

        // When
        Expense savedExpense = expenseService.addExpense(new Expense(), "test@example.com");

        // Then
        assertNotNull(savedExpense);
        assertEquals(1L, savedExpense.getUser().getId());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testDeleteExpense() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(testExpense));
        // Important: mock the void method call
        doNothing().when(expenseRepository).delete(testExpense);

        // When
        expenseService.deleteExpense(1L, "test@example.com");

        // Then
        // Verify that the delete method was called once on the repository
        verify(expenseRepository, times(1)).delete(testExpense);
    }
}