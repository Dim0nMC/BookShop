package com.example.bookshop.controller;

import org.springframework.core.io.Resource;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class BookController {

    @GetMapping("/book/download/{bookId}")
    public ResponseEntity<Resource> downloadBook(@PathVariable Long bookId) throws IOException {
        String fileName = bookId + ".pdf";
        Path path = Paths.get("BookShop/book_pdf", fileName); // Путь относительно текущего рабочего каталога

        System.out.println("Проверяем путь: " + path.toAbsolutePath());

        if (!Files.exists(path)) {
            System.out.println("Файл не найден: " + path.toAbsolutePath());
            return ResponseEntity.notFound().build();
        }

        Resource pdfResource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(pdfResource);
    }




}
