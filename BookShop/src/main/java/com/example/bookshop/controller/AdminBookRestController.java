package com.example.bookshop.controller;

import com.example.bookshop.dto.BookUpdateDTO;
import com.example.bookshop.model.Book;
import com.example.bookshop.service.BookService;
import com.example.bookshop.util.ValidationUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.bookshop.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = { AdminBookRestController.REST_URL},
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminBookRestController {
    static final String REST_URL = "/rest/admin/books";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BookService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<BookUpdateDTO> getAll() {
        log.info("getAll");
        return service.getAll().stream().map(BookUpdateDTO::new).collect(Collectors.toList());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") int id) {
        log.info("delete with id={}", id);
        service.delete(id);
    }

    @GetMapping("/filter-part/{part}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookUpdateDTO> findByPart (@PathVariable(name = "part") String part) {
        log.info("findByPart");
        return service.findByPart(part).stream().map(BookUpdateDTO::new).collect(Collectors.toList());
    }


    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookUpdateDTO get(
            @PathVariable("id") int id) {
        log.info("get {}", id);
        return new BookUpdateDTO(service.getById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
           @PathVariable("id") Integer id,
           @Valid @RequestBody BookUpdateDTO book) {
        log.info("update {} with id={}", book, id);
        ValidationUtil.assureIdConsistent(book, id);
        service.update(book);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookUpdateDTO> createWithLocation(
            @Valid @RequestBody BookUpdateDTO book) {
        checkNew(book);
        Book created = service.create(book);
        log.info("create {} with id={}", created, created.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(new BookUpdateDTO(created));
    }
}
