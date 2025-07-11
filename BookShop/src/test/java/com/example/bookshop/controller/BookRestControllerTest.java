package com.example.bookshop.controller;

import com.example.bookshop.controller.json.JsonUtil;
import com.example.bookshop.dto.BookUpdateDTO;
import com.example.bookshop.model.Book;
import com.example.bookshop.service.BookService;
import com.example.bookshop.util.exception.NotFoundException;
import jakarta.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.example.bookshop.BookTestData.*;
import static com.example.bookshop.TestUtil.*;
import static com.example.bookshop.UserTestData.admin;
import static com.example.bookshop.UserTestData.user;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class BookRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminBookRestController.REST_URL;

    @Autowired
    private ApplicationContext context;

    private BookService service;

    @PostConstruct
    public void init() {
        service = context.getBean(BookService.class);
    }

    @Test
    void findByPart() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/filter-part/" + PATH_VAR_FILTER_BY_PART)
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonIgnoringFields(find_books_dto, BookUpdateDTO.class, "image"));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" +BOOK1_ID)
                        .with(userHttpBasicWithEmail(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonIgnoringFields(book_dto1, BookUpdateDTO.class, "image"));
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" +BOOK1_ID)
                        .with(userHttpBasicWithEmail(user)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" +BOOK1_ID))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + NOT_FOUND_ID)
                        .with(userHttpBasicWithEmail(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        Book updated = getUpdated();
        BookUpdateDTO updatedDto = new BookUpdateDTO(updated);
        perform(MockMvcRequestBuilders.put(REST_URL + "/" +BOOK1_ID)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updatedDto)))
                .andDo(print());
        Assertions.assertThat(service.getById(BOOK1_ID)).
                usingRecursiveComparison().ignoringFields("image", "orderDetailsSet", "users", "genres", "authors").
                isEqualTo(updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Book newBook = getNew();
        BookUpdateDTO newBookDto = new BookUpdateDTO(newBook);
        ResultActions result = perform(MockMvcRequestBuilders.post(REST_URL)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(newBookDto)))
                .andDo(print());
        BookUpdateDTO actual = readFromJson(result, BookUpdateDTO.class);
        int newId = actual.getId();
        newBookDto.setId(newId);
        Assertions.assertThat(actual).usingRecursiveComparison().ignoringFields("image", "orderDetailsSet", "users").
                isEqualTo(newBookDto);
        newBook.setId(newId);
        Book act = service.getById(newId);
        System.out.println(act);
        Assertions.assertThat(act).usingRecursiveComparison().ignoringFields("image", "orderDetailsSet", "users", "genres", "authors").
                isEqualTo(newBook);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" +BOOK3_ID)
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.getById(BOOK3_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + NOT_FOUND_ID)
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteIntegrityViolation() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + BOOK1_ID)
                .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void createInvalid() throws Exception {
        BookUpdateDTO invalid = getNewDto();
        invalid.setName(null);
        invalid.setPage_count(0);
        perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        BookUpdateDTO invalid = new BookUpdateDTO(book2);
        invalid.setName(null);
        invalid.setPage_count(0);
        perform(MockMvcRequestBuilders.put(REST_URL + "/" +BOOK1_ID)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}