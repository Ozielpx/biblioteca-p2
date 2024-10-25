package com.biblioteca.locacao.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.locacao.Model.BookStatus;
import com.biblioteca.locacao.Model.Loan;
import com.biblioteca.locacao.Model.LoanStatus;
import com.biblioteca.locacao.Model.Customer;
import ch.qos.logback.core.status.Status;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private Customer service;

    @GetMapping("/{id}")
    public Customer findById(@PathVariable Long id) { 
        return service.findById(id); 
    }

    @GetMapping("/")
    public List<Customer> findByAttributes(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) LocalDate birthDate) {
        if (name != null) {
            return service.findByName(name);
        } else if (birthDate != null) {
            return service.findByBirthDate(birthDate);
        }
        return service.findAll();
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer) { 
        return service.save(customer); 
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { 
        service.delete(id); 
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        return service.save(customer);
    }

    @PatchMapping("/{id}/status")
    public Customer updateStatus(@PathVariable Long id, @RequestBody Status status) {
        Customer customer = service.findById(id);
        if (customer != null) {
            customer.setStatus(status);
            return service.save(customer);
        }
        return null;
    }
}
