package com.victor.barbearia.barbearia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/clientes").permitAll()
                        .requestMatchers(HttpMethod.POST, "/barbeiros").permitAll()
                        .requestMatchers("/auth/me").authenticated()

                        // Regras específicas de barbeiros devem vir ANTES do wildcard geral
                        .requestMatchers(HttpMethod.GET, "/barbeiros/*/horarios-disponiveis").authenticated()
                        .requestMatchers(HttpMethod.GET, "/barbeiros/*/folgas").authenticated()
                        .requestMatchers(HttpMethod.POST, "/barbeiros/*/folgas").hasRole("BARBEIRO")
                        .requestMatchers(HttpMethod.DELETE, "/barbeiros/*/folgas/**").hasRole("BARBEIRO")

                        .requestMatchers(HttpMethod.GET, "/servicos", "/servicos/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/barbeiros", "/barbeiros/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/agendamentos/cliente/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/agendamentos").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/agendamentos/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/produtos", "/produtos/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/configuracoes/horario").authenticated()
                        .requestMatchers(HttpMethod.GET, "/configuracoes/**").hasRole("BARBEIRO")
                        .requestMatchers(HttpMethod.PUT, "/configuracoes/**").hasRole("BARBEIRO")
                        .requestMatchers(HttpMethod.PATCH, "/agendamentos/*/status").hasRole("BARBEIRO")
                        .requestMatchers(HttpMethod.PUT, "/agendamentos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/clientes/**").hasRole("BARBEIRO")
                        .requestMatchers(HttpMethod.PUT, "/barbeiros/**").hasRole("BARBEIRO")
                        .requestMatchers(HttpMethod.PUT, "/servicos/**").hasRole("BARBEIRO")
                        .requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("BARBEIRO")
                        .anyRequest().hasRole("BARBEIRO")
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
