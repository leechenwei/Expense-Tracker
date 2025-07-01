package com.expensetracker.backend.controller;

import com.expensetracker.backend.model.Expense;
import com.expensetracker.backend.security.JwtUtil;
import com.expensetracker.backend.security.UserDetailsServiceImpl;
import com.expensetracker.backend.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    // We need to mock these as they are part of the security configuration
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    // --- Security Tests ---

    @Test
    void shouldReturn401_WhenGettingExpensesUnauthenticated() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isUnauthorized());
    }

    // --- Controller Logic Tests (with authenticated user) ---

    @Test
    @WithMockUser(username = "test@example.com")
    void shouldFetchAllExpensesForAuthenticatedUser() throws Exception {
        // Given
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setDescription("Coffee");
        when(expenseService.getAllExpensesForUser("test@example.com"))
                .thenReturn(Collections.singletonList(expense));

        // When & Then
        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Coffee"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void shouldCreateExpense() throws Exception {
        // Given
        Expense expense = new Expense();
        expense.setDescription("New Laptop");
        expense.setAmount(new BigDecimal("1200.00"));
        expense.setCategory("Technology");
        expense.setDate(LocalDate.now());

        when(expenseService.addExpense(any(Expense.class), anyString())).thenReturn(expense);

        // When & Then
        mockMvc.perform(post("/api/expenses")
                        .with(csrf()) // Include CSRF token for POST
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("New Laptop"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void shouldDeleteExpense() throws Exception {
        // Given
        // Service's delete method is void, so no 'when' is needed for its return

        // When & Then
        mockMvc.perform(delete("/api/expenses/1")
                        .with(csrf())) // Include CSRF token for DELETE
                .andExpect(status().isOk());
    }
}