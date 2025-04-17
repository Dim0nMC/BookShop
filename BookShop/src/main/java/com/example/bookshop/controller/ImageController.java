package com.example.bookshop.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {
    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) throws IOException {
        Path uploadPath = Paths.get("BookShop", "uploads").resolve(filename);
        System.out.println("Абсолютный путь: " + uploadPath.toAbsolutePath());
        System.out.println("Working dir: " + System.getProperty("user.dir"));
        Resource file = new UrlResource(uploadPath.toUri());
        if (file.exists() && file.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(file);
        } else {
            throw new FileNotFoundException("Файл не найден: " + filename);
        }
    }

}
