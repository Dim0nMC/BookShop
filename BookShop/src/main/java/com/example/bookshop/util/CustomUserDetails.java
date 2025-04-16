package com.example.bookshop.util;

import com.example.bookshop.model.Role;
import com.example.bookshop.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Преобразуем роли пользователя в список GrantedAuthority, например:
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())
        );
    }

    public int getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // должен быть зашифрованным (например, BCrypt)
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // или user.getUsername(), в зависимости от твоей модели
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // или реализуй логику проверки
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // или реализуй логику проверки
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // или реализуй логику проверки
    }

    @Override
    public boolean isEnabled() {
        return true; // например, может быть флаг активности аккаунта
    }
}
