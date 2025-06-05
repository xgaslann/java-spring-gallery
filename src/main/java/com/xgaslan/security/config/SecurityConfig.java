package com.xgaslan.security.config;

import com.xgaslan.security.PublicEndpointRegistry;
import com.xgaslan.security.jwt.AuthEntryPoint;
import com.xgaslan.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PublicEndpointRegistry publicEndpointRegistry;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthEntryPoint authEntryPoint;
    private final AppConfig appConfig;

    @Autowired
    public SecurityConfig(PublicEndpointRegistry publicEndpointRegistry, JwtAuthenticationFilter jwtAuthenticationFilter, AuthEntryPoint authEntryPoint, AppConfig appConfig) {
        this.publicEndpointRegistry = publicEndpointRegistry;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authEntryPoint = authEntryPoint;
        this.appConfig = appConfig;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers(publicEndpointRegistry.getPublicEndpoints().toArray(new String[0]))
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(appConfig.authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
