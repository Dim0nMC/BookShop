package com.example.bookshop.controller;

import com.example.bookshop.controller.json.JsonUtil;
import com.example.bookshop.model.Author;
import com.example.bookshop.service.AuthorService;
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

import static com.example.bookshop.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.example.bookshop.TestUtil.*;
import static com.example.bookshop.AuthorTestData.*;


class AuthorRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminAuthorRestController.REST_URL;

    @Autowired
    private ApplicationContext context;

    private AuthorService service;

    @PostConstruct
    public void init() {
        service = context.getBean(AuthorService.class);
    }

    @Test
    void getBySurname() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/filter-surname/" + PATH_VAR_FILTER_BY_SURNAME)
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(List.of(author_id2), Author.class));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + AUTHOR1_ID)
                        .with(userHttpBasicWithEmail(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(author_id1, Author.class));
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + AUTHOR1_ID)
                        .with(userHttpBasicWithEmail(user)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + AUTHOR1_ID))
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
        Author updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + AUTHOR1_ID)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated)))
                .andDo(print());
        Assertions.assertThat(service.findById(AUTHOR1_ID)).
                usingRecursiveComparison().
                comparingOnlyFields("id", "name", "surname").
                isEqualTo(updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Author newAuthor = getNew();
        ResultActions result = perform(MockMvcRequestBuilders.post(REST_URL)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(newAuthor)))
                .andDo(print());
        Author actual = readFromJson(result, Author.class);
        int newId = actual.getId();
        newAuthor.setId(newId);
        Assertions.assertThat(actual).usingRecursiveComparison().
                comparingOnlyFields("id", "name", "surname").
                isEqualTo(newAuthor);
        Assertions.assertThat(service.findById(newId)).usingRecursiveComparison().
                comparingOnlyFields("id", "name", "surname").
                isEqualTo(newAuthor);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + AUTHOR1_ID)
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.findById(AUTHOR1_ID));
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
        Author invalid = new Author(null, null, "");
        perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasicWithEmail(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        Author invalid = new Author(AUTHOR1_ID, null, "");
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + AUTHOR1_ID)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Author clone = new Author(null, author_id1.getName(), author_id1.getSurname());
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
        Author invalid = new Author(AUTHOR1_ID, author_id2.getName(), author_id2.getSurname());
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + AUTHOR1_ID)
                        .with(userHttpBasicWithEmail(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}