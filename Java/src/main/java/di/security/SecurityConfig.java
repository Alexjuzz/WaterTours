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
