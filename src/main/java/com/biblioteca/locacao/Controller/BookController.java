package com.biblioteca.locacao.Controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.locacao.Model.Book;
import com.biblioteca.locacao.Model.BookStatus;
import com.biblioteca.locacao.Service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping
    public List<Book> findAll() { 
        return service.findAll(); 
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) { 
        return service.findById(id); 
    }

    @PostMapping
    public Book create(@RequestBody Book book) { 
        return service.save(book); 
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { 
        service.delete(id); 
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        return service.save(book);
    }

    @PatchMapping("/{id}/status")
    public Book updateStatus(@PathVariable Long id, @RequestBody BookStatus status) {
        Book book = service.findById(id);
        if (book != null) {
            book.setStatus(status);
            return service.save(book);
        }
        return null;
    }
}
