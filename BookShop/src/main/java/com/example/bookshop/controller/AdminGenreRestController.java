package com.example.bookshop.controller;

import com.example.bookshop.model.Genre;
import com.example.bookshop.service.GenreService;
import com.example.bookshop.util.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = { AdminGenreRestController.REST_URL},
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Tag(name = "Endpoints for manage Genres (for users with admin rights)")
public class AdminGenreRestController {
    static final String REST_URL = "/rest/admin/genres";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GenreService service;

    @Operation(summary = "Return all Genres", description = "Retrieving the collection of Genres")
    @ApiResponses({@ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Genre.class), mediaType = "application/json"))})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Genre> getAll() {
        log.info("getAll");
        return service.getAllGenres();
    }


//    @ApiOperation(value = "Genre object by id", response = Restaurant.class)
//    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Genre.class),
//            @ApiResponse(code = 404, message = "Data no found")})

    @Operation(summary = "Genre object by id", description = "Retrieving single Genre object")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Genre.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Data no found")
    })
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    Genre get(
            //@ApiParam(value = "Id of the retrieving Genre object. Cannot be empty.", required = true)
            @Parameter(name = "id", description = "Id of the retrieving Genre object. Cannot be empty.", required = true)
            @PathVariable("id") int id) {
        log.info("get {}", id);
        Genre g = service.findById(id);
        System.out.println(g);
        return g;
//        return service.findById(id);
    }

//    @ApiOperation(value = "Update Genre object by id")
//    @ApiResponses({@ApiResponse(code = 204, message = "No response content"),
//            @ApiResponse(code = 404, message = "Data no found")})
    @Operation(summary = "Update Genre object by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No response content"),
            @ApiResponse(responseCode = "404", description = "Data no found")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Parameters({
            @Parameter(name = "id", description = "Id of the updating Genre object. Cannot be empty.", required = true),
            @Parameter(name = "genre", description = "Genre object store in database. Cannot be empty.", required = true)
    })
    public void update(
//            @ApiParam(value = "Id of the updating Genre object. Cannot be empty.", required = true)
//            @ApiParam(value = "Genre object store in database. Cannot be empty.", required = true)
           @PathVariable("id") Integer id,
           @Valid @RequestBody Genre genre) {
        log.info("update {} with id={}", genre, id);
        ValidationUtil.assureIdConsistent(genre, id);
        service.update(genre);
    }

//    @ApiOperation(value = "Create Genre object", response = ResponseEntity.class)
//    @ApiResponses({@ApiResponse(code = 201, message = "Successfully created new Genre")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Genre object")
    @ApiResponse(responseCode = "201", description = "Successfully created new Genre")
    @Parameter(name = "genre", description = "Genre object store in database. Cannot be empty.", required = true)
    public ResponseEntity<Genre> createWithLocation(
//            @ApiParam(value = "Genre object store in database. Cannot be empty.", required = true)
            @Valid @RequestBody Genre genre) {
        checkNew(genre);
        Genre created = service.create(genre);
        log.info("create {} with id={}", created, created.getId());
        
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

//    @ApiOperation(value = "Delete Genre object with specified id")
//    @ApiResponses({@ApiResponse(code = 204, message = "No response content")})
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Genre object with specified id")
    @ApiResponse(responseCode = "204", description = "No response content")
    @Parameter(name = "id", description = "ID of the Genre object to be deleted. Cannot be empty.", required = true)
    public void delete(
//            @ApiParam(value = "ID of the Genre object to be deleted. Cannot be empty.", required = true)
            @PathVariable("id") int id) {
        log.info("delete with id={}", id);
        service.delete(id);
    }
}
