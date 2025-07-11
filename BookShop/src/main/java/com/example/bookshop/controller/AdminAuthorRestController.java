package com.example.bookshop.controller;

import com.example.bookshop.model.Author;
import com.example.bookshop.service.AuthorService;
import com.example.bookshop.util.ValidationUtil;
//import io.swagger.annotations.*;
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

import static com.example.bookshop.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = { AdminAuthorRestController.REST_URL},
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//@Api(description = "Endpoints for manage Authors (for users with admin rights)", produces = "application/json")
public class AdminAuthorRestController {
    static final String REST_URL = "/rest/admin/authors";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthorService service;

//    @ApiOperation(value = "Return all Authors", notes = "Retrieving the collection of Authors", response = Author[].class)
//    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Author[].class)})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Author> getAll() {
        log.info("getAll");
        return service.findAll();
    }


    @GetMapping("/filter-surname/{surname}")
    @ResponseStatus(HttpStatus.OK)
    public List<Author> getBySurname (@PathVariable(name = "surname") String surname) {
        log.info("getBySurname");
        List<Author> a = service.findBySurname(surname);
        return a;
    }

//    @ApiOperation(value = "Author restaurant object by id", response = Restaurant.class)
//    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Author.class),
//            @ApiResponse(code = 404, message = "Data no found")})
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    Author get(
            //@ApiParam(value = "Id of the retrieving Author object. Cannot be empty.", required = true)
            @PathVariable("id") int id) {
        log.info("get {}", id);
        return service.findById(id);
    }

//    @ApiOperation(value = "Update Author object by id")
//    @ApiResponses({@ApiResponse(code = 204, message = "No response content"),
//            @ApiResponse(code = 404, message = "Data no found")})
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
           @PathVariable("id") Integer id,
           @Valid @RequestBody Author author) {
        log.info("update {} with id={}", author, id);
        ValidationUtil.assureIdConsistent(author, id);
        service.update(author);
    }

//    @ApiOperation(value = "Create Author object", response = ResponseEntity.class)
//    @ApiResponses({@ApiResponse(code = 201, message = "Successfully created new Author")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Author> createWithLocation(
//            @ApiParam(value = "Author object store in database. Cannot be empty.", required = true)
            @Valid @RequestBody Author author) {
        checkNew(author);
        Author created = service.create(author);
        log.info("create {} with id={}", created, created.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

//    @ApiOperation(value = "Delete Author object with specified id")
//    @ApiResponses({@ApiResponse(code = 204, message = "No response content")})
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
//            @ApiParam(value = "ID of the Author object to be deleted. Cannot be empty.", required = true)
            @PathVariable("id") int id) {
        log.info("delete with id={}", id);
        service.delete(id);
    }
}
