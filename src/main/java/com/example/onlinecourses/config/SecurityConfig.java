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
                        .requestMatchers("/register", "/login", "/").permitAll() // Strony publiczne
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Dostęp dla administratorów
                        .requestMatchers("/user/**").hasRole("USER") // Dostęp dla użytkowników
                        .requestMatchers("/index").hasRole("USER") // Dostęp do /index tylko dla zalogowanych użytkowników
                        .anyRequest().authenticated()) // Wszystkie inne strony wymagają logowania

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            // Przekierowanie po zalogowaniu na odpowiednią stronę
                            boolean isAdmin = authentication.getAuthorities().stream()
                                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

                            if (isAdmin) {
                                response.sendRedirect("/admin");
                            } else {
                                response.sendRedirect("/index");
                            }
                        })
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID") // Add this line
                        .permitAll())

                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/login"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
