//package di.security;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Отключаем CSRF
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()).csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());// Требуем аутентификацию для всех запросов
//                // Включаем HTTP Basic аутентификацию с настройками по умолчанию
//        return http.build();
//    }
//}


//TODO 29.08 - тест JWT

import di.model.entity.user.User;
import di.security.jwt.JwtAuthenticationFilter;
import di.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;
    private final UserService userService;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter authenticationFilter, UserService userService) {
        this.authenticationFilter = authenticationFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
}