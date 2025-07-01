package com.expensetracker.backend.controller;

import com.expensetracker.backend.model.Expense;
import com.expensetracker.backend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<Expense> getAllExpenses(@AuthenticationPrincipal UserDetails userDetails) {
        return expenseService.getAllExpensesForUser(userDetails.getUsername());
    }

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense, @AuthenticationPrincipal UserDetails userDetails) {
        return expenseService.addExpense(expense, userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Expense expense = expenseService.getExpenseById(id, userDetails.getUsername());
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expenseDetails, @AuthenticationPrincipal UserDetails userDetails) {
        Expense updatedExpense = expenseService.updateExpense(id, expenseDetails, userDetails.getUsername());
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        expenseService.deleteExpense(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}