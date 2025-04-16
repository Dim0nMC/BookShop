package com.example.bookshop.config;

import com.example.bookshop.service.CustomUserDetailsService;
import com.example.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Здесь твоя реализация поиска пользователя
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // отключаем CSRF (можно оставить, если ты защищаешь формы)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/search", "/book/{id}",  "/register", "/login", "/styles.css", "/images/**", "/uploads/**").permitAll() // общедоступные ресурсы
                        .requestMatchers("/admin/**").hasRole("ADMIN") // доступ только для админов
                        .anyRequest().authenticated() // все остальные требуют авторизации
                )
                .formLogin(login -> login
                        .loginPage("/login") // своя кастомная страница логина
                        .loginProcessingUrl("/perform_login") // форма будет отправляться сюда
                        .defaultSuccessUrl("/", true) // куда перенаправлять после успешного входа
                        .failureUrl("/genres") // при ошибке логина
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

}
