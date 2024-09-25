package com.emazon.stock.infraestructura.configuration;

import com.emazon.stock.infraestructura.configuration.jwt.JwtAuthenticationFilter;
import com.emazon.stock.infraestructura.configuration.jwt.JwtUtils;
import com.emazon.stock.infraestructura.exceptionhandler.CustomAccessDeniedHandler;
import com.emazon.stock.infraestructura.exceptionhandler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.emazon.stock.infraestructura.util.InfraestructureRestControllerConstants.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtils jwtUtils;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(HttpMethod.POST, API_BRAND, API_CATEGORY, API_PRODUCT).hasRole(ADMIN);
                    http.requestMatchers(HttpMethod.PUT, API_PRODUCT_ADD_SUPPLY).hasRole(AUX_BODEGA);
                    http.anyRequest().permitAll();
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .build();
    }
}
