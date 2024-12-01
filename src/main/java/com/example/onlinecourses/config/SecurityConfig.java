package com.example.onlinecourses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/login", "/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login") // Strona logowania
                        .loginProcessingUrl("/login") // Endpoint obsługujący logowanie
                        .defaultSuccessUrl("/", true) // Domyślna strona po zalogowaniu
                        .failureUrl("/login?error=true") // Strona po nieudanym logowaniu
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout") // Endpoint wylogowania
                        .logoutSuccessUrl("/login?logout=true") // Po wylogowaniu
                        .invalidateHttpSession(true) // Unieważnienie sesji
                        .deleteCookies("JSESSIONID") // Usunięcie ciasteczek sesji
                        .permitAll());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

