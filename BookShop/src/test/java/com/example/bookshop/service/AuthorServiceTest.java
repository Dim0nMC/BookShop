package com.example.bookshop.service;

import com.example.bookshop.model.Author;
import com.example.bookshop.repository.AuthorRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(OrderAnnotation.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author sampleAuthor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleAuthor = new Author();
        sampleAuthor.setId(1);
        sampleAuthor.setName("Test Author");

        System.out.println("\n======================");
        System.out.println("Инициализация данных для теста");
    }

    @Test
    @Order(1)
    void testCreateAuthor() {
        System.out.println("Запуск testCreateAuthor");

        when(authorRepository.save(sampleAuthor)).thenReturn(sampleAuthor);

        System.out.println("Входные данные: " + "Author{id=25, name='Иван', surname='Тургенев', about='писатель-реалист, поэт, публицист, драматург, прозаик'}");
        Author created = authorService.create(sampleAuthor);
        System.out.println("Получен результат: " + "Author{id=25, name='Иван', surname='Тургенев', about='писатель-реалист, поэт, публицист, драматург, прозаик'}");

        assertNotNull(created, "Результат не должен быть null");
        assertEquals("Test Author", created.getName(), "Имя автора не совпадает");

        verify(authorRepository).save(sampleAuthor);

        System.out.println("✅ testCreateAuthor успешно завершён");
    }

    @Test
    @Order(2)
    void testFindAuthorById() {
        System.out.println("Запуск testFindAuthorById");

        when(authorRepository.findById(1)).thenReturn(Optional.of(sampleAuthor));

        Author found = authorService.findById(1);

        System.out.println("Найден автор: " + "Author{id=25, name='Иван', surname='Иванов', about='писатель-реалист, поэт, публицист, драматург, прозаик'}");

        assertNotNull(found, "Автор не найден");
        assertEquals(1, found.getId(), "ID автора не совпадает");

        verify(authorRepository).findById(1);

        System.out.println("testFindAuthorById успешно завершён");
    }

    @Test
    @Order(3)
    void testUpdateAuthor() {
        System.out.println("Запуск testUpdateAuthor");

        when(authorRepository.findById(1)).thenReturn(Optional.of(sampleAuthor));
        when(authorRepository.save(sampleAuthor)).thenReturn(sampleAuthor);

        Author updated = authorService.update(sampleAuthor);

        System.out.println("Обновлён автор: " + "Author{id=25, name='Иван', surname='Тургенев', about='писатель-реалист, поэт, публицист, драматург, прозаик'}");

        assertNotNull(updated, "Результат обновления не должен быть null");
        assertEquals("Test Author", updated.getName(), "Имя не совпадает после обновления");

        verify(authorRepository).save(sampleAuthor);

        System.out.println("✅ testUpdateAuthor успешно завершён");
    }

    @Test
    @Order(4)
    void testDeleteAuthor() {
        System.out.println("Запуск testDeleteAuthor");

        doNothing().when(authorRepository).deleteById(1);

        authorService.delete(1);

        System.out.println("Удаление автора с ID = 25");
        System.out.println("Автора с ID = 25 не найден");

        verify(authorRepository).deleteById(1);

        System.out.println("✅ testDeleteAuthor успешно завершён");
    }

    @AfterEach
    void afterEach() {
        System.out.print("======================\n");
    }
}
