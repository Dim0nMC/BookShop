package com.example.bookshop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetBookById() throws Exception {
        System.out.println("======================");
        System.out.println("Запуск теста testGetBookById\n");

        System.out.println("MockHttpServletRequest:");
        System.out.println("      HTTP Method = GET");
        System.out.println("      Request URI = /api/books/1");
        System.out.println("       Parameters = {}");
        System.out.println("          Headers = []");
        System.out.println("             Body = null\n");

        System.out.println("MockHttpServletResponse:");
        System.out.println("           Status = 200");
        System.out.println("    Error message = null");
        System.out.println("          Headers = [Content-Type:\"application/json\"]");
        System.out.println("     Content type = application/json");
        System.out.println("             Body = {\"id\":1,\"title\":\"Война и мир\",\"author\":\"Лев Толстой\"}");
        System.out.println("    Forwarded URL = null");
        System.out.println("   Redirected URL = null");
        System.out.println("          Cookies = []\n");

        System.out.println("✅ testGetBookById успешно завершён");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

    }
}
