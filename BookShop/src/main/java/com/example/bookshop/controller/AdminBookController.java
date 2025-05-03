package com.example.bookshop.controller;

import com.example.bookshop.dto.BookAddDTO;
import com.example.bookshop.dto.BookUpdateDTO;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/books")
public class AdminBookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody BookAddDTO book) {
        bookService.create(book);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody BookUpdateDTO book) {
        bookService.update(book);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<BookUpdateDTO> get(@PathVariable int id) {
        Book book = bookService.getById(id);
        return ResponseEntity.ok(new BookUpdateDTO(book));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Книга с ID " + id + " не найдена");
        }

        bookService.delete(id);
        return ResponseEntity.ok("Книга успешно удалена");
    }




}
