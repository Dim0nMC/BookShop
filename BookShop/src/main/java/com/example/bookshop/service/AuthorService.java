package com.example.bookshop.service;

import com.example.bookshop.model.Author;
import com.example.bookshop.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.example.bookshop.util.ValidationUtil.checkNotFoundWithId;

@Service
public class AuthorService {
    private final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public Author create(Author author){
        Assert.notNull(author, "Author must not be null");
        return authorRepository.save(author);
    }

    @Cacheable(value = "authors_list")
    public List<Author> findAll(){
        return authorRepository.findAll(SORT_NAME);
    }

    @Cacheable(value = "authors", key = "#id")
    public Author findById(Integer id){
        return checkNotFoundWithId(authorRepository.findById(id).orElse(null), id);
//        return  authorRepository.findById(id).orElse(null);
    }

    public Author update(Author author){
        Assert.notNull(author, "Author must not be null");
        Assert.notNull(author.getId(), "Author id must not be null");
        return authorRepository.save(author);
    }

    public void delete(int id){
        authorRepository.delete(id);
        // Если нужно, добавьте проверку, если книга не существует
        checkNotFoundWithId(true, id);
    }
}
