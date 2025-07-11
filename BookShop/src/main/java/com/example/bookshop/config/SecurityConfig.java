package com.example.bookshop.config;

import com.example.bookshop.service.UserService;
import com.example.bookshop.util.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity(debug = true)

public class SecurityConfig {

    private final UserService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationSuccessHandler successHandler;


//    public static class RestConfiguration extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.antMatcher("/rest/**").
//                    sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
//                    httpBasic().and().
//                    csrf().disable().
//                    authorizeRequests().
//                    antMatchers("/rest/admin/**").hasRole("ADMIN").
//                    antMatchers("/rest/profile/register").anonymous().
//                    antMatchers("/rest/**").authenticated();
//        }
//    }


    @Autowired
    public SecurityConfig(UserService userDetailsService, PasswordEncoder passwordEncoder, CustomAuthenticationSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.successHandler = successHandler;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Здесь твоя реализация поиска пользователя
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain basicAuthSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/rest/admin/**")
                .authorizeHttpRequests(auth -> auth.
                        requestMatchers("/rest/admin/**").hasRole("ADMIN")
                ).csrf().disable()
                .httpBasic(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/**", "/api-docs/**").permitAll()
                        .requestMatchers("/", "/search", "/book/{id}", "/register", "/login", "/test-auth", "/styles.css", "/images/**", "/uploads/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/genres")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .authenticationProvider(authenticationProvider(passwordEncoder));

        return http.build();
    }


}
