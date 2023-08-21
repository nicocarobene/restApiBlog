package com.sistem.blog.config;

import com.sistem.blog.Security.JwtAuthenticationEntryPoint;
import com.sistem.blog.Security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> {
                    authz
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher( "/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/comment/**")).authenticated()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/comment/**")).hasRole("ADMIN")
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/post/**")).authenticated()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/post/**")).hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                //.httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement->{
                    sessionManagement
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}





    /*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User
                .builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin= User
                .builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(userDetails, admin);
    }
    */




