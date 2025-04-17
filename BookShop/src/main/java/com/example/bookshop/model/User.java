package com.example.bookshop.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity{
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @ManyToMany
    @JoinTable(name = "cart",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> cart;

    @ManyToMany
    @JoinTable(name = "books_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books;

    @NotBlank
    @Size(min = 5, max = 20)
    private String name;

    private String image;

    @Email
    @NotBlank
    @Size(min = 7, max = 100)
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank
    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Некорректный номер телефона. Допустимы только цифры, длина от 10 до 15 символов, может начинаться с '+'"
    )
    private String phone;

    @NotNull
    private int age;

    public User() {}

    public User(Role role, List<Order> orders, Set<Book> cart, Set<Book> books, String name, String image, String email, String password, String phone, int age) {
        this.role = role;
        this.orders = orders;
        this.cart = cart;
        this.books = books;
        this.name = name;
        this.image = image;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Set<Book> getCart() {
        return cart;
    }

    public void setCart(Set<Book> cart) {
        this.cart = cart;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
