package com.springbootwhatspp.config;

import com.springbootwhatspp.jwt.JwtAuthenticationEntryPoint;
import com.springbootwhatspp.jwt.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                //.cors().configurationSource(new CorsConfigurationSource() {
                //  @Override
                //public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                //  CorsConfiguration corsConfiguration = new CorsConfiguration();
                //corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                //corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                //corsConfiguration.setAllowCredentials(true);
                //corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                //corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
                //corsConfiguration.setMaxAge(3600l);

                //return corsConfiguration;
                //}
                //});
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))

        ;

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
