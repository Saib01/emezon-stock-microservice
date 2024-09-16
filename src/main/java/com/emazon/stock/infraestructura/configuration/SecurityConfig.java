package com.emazon.stock.infraestructura.configuration;
import com.emazon.stock.infraestructura.configuration.jwt.JwtAuthenticationFilter;
import com.emazon.stock.infraestructura.configuration.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(http->{
                    http.requestMatchers(HttpMethod.POST,"/brand/","/category/","/product/").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT,"/product/*/add-supply").hasRole("AUX_BODEGA");
                    http.anyRequest().permitAll();
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
