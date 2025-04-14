package com.example.bookshop.service;

import com.example.bookshop.model.Author;
import com.example.bookshop.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public Author create(Author author){
        return authorRepository.save(author);
    }

    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    public Author findById(Integer id){
        return  authorRepository.findById(id).orElse(null);
    }

    public Author update(Author author){
        if(authorRepository.findById(author.getId())!=null){
            return authorRepository.save(author);
        }
        return authorRepository.save(author);
    }

    public void delete(Integer id){
        authorRepository.deleteById(id);
    }
}
