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
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/teacher/**").hasRole("TEACHER")
                        .requestMatchers("/student/**").hasRole("STUDENT")
//                        .requestMatchers("/index").hasRole("USER")
//                        .requestMatchers("/student/home").hasRole("USER")
//                        .requestMatchers("/teacher/home").hasRole("TEACHER")
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            // Przekierowanie po zalogowaniu na odpowiednią stronę
                            boolean isAdmin = authentication.getAuthorities().stream()
                                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
                            boolean isTeacher = authentication.getAuthorities().stream()
                                    .anyMatch(role -> role.getAuthority().equals("ROLE_TEACHER"));

                            if (isAdmin) {
                                response.sendRedirect("/admin");
                            } else if(isTeacher) {
                                response.sendRedirect("/teacher/home");
                            }else{
                                response.sendRedirect("/student/home");
                            }
                        })
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
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
