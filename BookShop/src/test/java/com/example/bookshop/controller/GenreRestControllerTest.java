package com.example.bookshop.controller;

import com.example.bookshop.controller.json.JsonUtil;
import com.example.bookshop.model.Genre;
import com.example.bookshop.service.GenreService;
import com.example.bookshop.util.exception.NotFoundException;
import jakarta.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.bookshop.GenreTestData.*;
import static com.example.bookshop.TestUtil.*;
import static com.example.bookshop.UserTestData.admin;
import static com.example.bookshop.UserTestData.user;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class GenreRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminGenreRestController.REST_URL;

    @Autowired
    private ApplicationContext context;

    private GenreService service;

    @PostConstruct
    public void init() {
        service = context.getBean(GenreService.class);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + GENRE1_ID)
                        .with(userHttpBasicWithEmail(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonIgnoringFields(genre1, Genre.class, "books"));
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + GENRE1_ID)
                        .with(userHttpBasicWithEmail(user)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + GENRE1_ID))
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
        Genre updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + GENRE1_ID)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated)))
                .andDo(print());
        Assertions.assertThat(service.findById(GENRE1_ID)).
                usingRecursiveComparison().
                comparingOnlyFields("id", "name").
                isEqualTo(updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Genre newGenre = getNew();
        ResultActions result = perform(MockMvcRequestBuilders.post(REST_URL)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(newGenre)))
                .andDo(print());
        Genre actual = readFromJson(result, Genre.class);
        int newId = actual.getId();
        newGenre.setId(newId);
        Assertions.assertThat(actual).usingRecursiveComparison().
                comparingOnlyFields("id", "name").
                isEqualTo(newGenre);
        Assertions.assertThat(service.findById(newId)).usingRecursiveComparison().
                comparingOnlyFields("id", "name").
                isEqualTo(newGenre);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + GENRE1_ID)
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.findById(GENRE1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + NOT_FOUND_ID)
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void createInvalid() throws Exception {
        Genre invalid = new Genre((Integer) null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        Genre invalid = new Genre(GENRE1_ID, "");
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + GENRE1_ID)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Genre clone = new Genre(null, genre1.getName());
        perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(clone))
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Genre invalid = new Genre(GENRE1_ID, genre2.getName());
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + GENRE1_ID)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}