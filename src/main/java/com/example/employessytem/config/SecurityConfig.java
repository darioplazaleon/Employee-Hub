package com.example.employessytem.config;

import static com.example.employessytem.entity.Permission.ADMIN_WRITE;
import static com.example.employessytem.entity.Role.*;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthFilter jwtAuthFilter;

  private static final String[] SWAGGER_AUTH_WHITELIST = {
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/swagger-resources/**",
    "/swagger-ui.html",
    "/webjars/**",
    "/api/v1/auth/login",
    "/api/v1/auth/refresh"
  };

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            req ->
                req.requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/.*",
                        "/v3/api-docs/.*",
                        "/v3/api-docs",
                        "/favicon.ico",
                        "/api/v1/auth/login",
                        "/api/v3/auth/refresh")
                    .permitAll()
                    .requestMatchers(
                        "/api/v1/vacation/reject/**",
                        "/api/v1/vacation/approve/**",
                        "/api/v1/vacation/employee/**",
                        "/api/v1/vacation/all")
                    .hasAnyRole(ADMIN.name(), MANAGER.name())
                    .requestMatchers("/api/v1/vacation/request")
                    .hasAnyRole(ADMIN.name(), MANAGER.name(), USER.name())
                    .requestMatchers("/api/v1/user/{id}", "/api/v1/user/{id}", "/api/v1/user/{id}")
                    .hasAnyRole(ADMIN.name(), MANAGER.name(), USER.name())
                    .requestMatchers(HttpMethod.POST ,"/api/v1/user/create/**")
                    .hasAnyRole(ADMIN.name())
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    //                .logout(logout -> logout.logoutUrl("/auth/logout")
    //                        .addLogoutHandler(this::logout)
    //                        .logoutSuccessHandler((req, res, auth) ->
    // SecurityContextHolder.clearContext()));
    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers(SWAGGER_AUTH_WHITELIST);
  }
}
