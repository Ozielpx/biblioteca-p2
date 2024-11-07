package com.biblioteca.locacao.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.locacao.Model.BookStatus;
import com.biblioteca.locacao.Model.Loan;
import com.biblioteca.locacao.Model.LoanStatus;
import com.biblioteca.locacao.Service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    @Autowired
    private LoanService service;

    @GetMapping
    public List<Loan> findAll() { 
        return service.findAll(); 
    }

    @GetMapping("/")
    public List<Loan> findByCustomer(@RequestParam Long customerId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        if (customerId != null) {
            return service.findByCustomerId(customerId);
        } else if (startDate != null && endDate != null) {
            return service.findByDateRange(startDate, endDate);
        }
        return service.findAll();
    }

    @PostMapping
    public Loan create(@RequestBody Loan loan) {
        loan.setStatus(LoanStatus.ONGOING);
        return service.save(loan);
    }

    @PatchMapping("/{id}/extend")
    public Loan extendLoan(@PathVariable Long id, @RequestBody LocalDate newReturnDate) {
        Loan loan = service.findById(id);
        if (loan != null) {
            loan.setReturnDate(newReturnDate);
            return service.save(loan);
        }
        return null;
    }

    @PatchMapping("/{id}/finish")
    public Loan finishLoan(@PathVariable Long id) {
        Loan loan = service.findById(id);
        if (loan != null) {
            loan.setStatus(LoanStatus.FINISHED);
            loan.getBooks().forEach(book -> book.setStatus(BookStatus.AVAILABLE));
            return service.save(loan);
        }
        return null;
    }
}

