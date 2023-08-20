package com.sistem.blog.config;

import com.sistem.blog.Security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService userDetailService;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> {
                    authz
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PATCH, "/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/**")).authenticated()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/**")).hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }





    /*
        @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        System.out.println(auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder()));
    }

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
    @Bean
    public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> {
                    authz
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PATCH,"/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/h2-console/**")).permitAll();
                })
                .csrf(csrf-> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> {
                    authz
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/**")).authenticated()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/**")).hasRole("ADMIN");
                })
                //.httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }
    */

}


