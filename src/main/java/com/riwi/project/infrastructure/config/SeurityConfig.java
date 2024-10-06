package com.riwi.project.infrastructure.config;

import com.riwi.project.infrastructure.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.riwi.project.domain.enums.UserPermision.*;
import static com.riwi.project.domain.enums.UserRole.ADMIN;
import static com.riwi.project.domain.enums.UserRole.USER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SeurityConfig {
    @Autowired
    private UserDetailsService userService;

    @Autowired
    JwtAuthFilter jwtFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(customizer -> customizer.disable())
                 .authorizeHttpRequests(request -> request
                         .requestMatchers("api/v1/auth/**")
                         .permitAll()
                         //Secure Task Endpoint
                         .requestMatchers("/api/v1/tasks/**").hasAnyRole(ADMIN.name(),USER.name())
                         .requestMatchers(GET, "/api/v1/tasks/**").hasAnyRole(ADMIN_READ.name(), USER_READ.name())
                         .requestMatchers(PUT, "/api/v1/tasks/**").hasAnyRole(ADMIN_UPDATE.name(), USER_UPDATE.name())
                         .requestMatchers(POST, "/api/v1/tasks/**").hasAnyRole(ADMIN_CREATE.name())
                         .requestMatchers(DELETE, "/api/v1/tasks/**").hasAnyRole(ADMIN_DELETE.name())

                         //Secure Project Endpoint
                         .requestMatchers("/api/v1/projects/**").hasAnyRole(ADMIN.name(),USER.name())
                         .requestMatchers(GET, "/api/v1/projects/**").hasAnyRole(ADMIN_READ.name(), USER_READ.name())
                         .requestMatchers(PUT, "/api/v1/projects/**").hasAnyRole(ADMIN_UPDATE.name())
                         .requestMatchers(POST, "/api/v1/projects/**").hasAnyRole(ADMIN_CREATE.name())
                         .requestMatchers(DELETE, "/api/v1/projects/**").hasAnyRole(ADMIN_DELETE.name())

                         //Secure User Endpoint
                         .requestMatchers("api/v1/users/**").hasAnyRole(ADMIN.name(),USER.name())
                         .requestMatchers(GET, "api/v1/users/**").hasAnyRole(ADMIN_READ.name(), USER_READ.name())
                         .requestMatchers(PUT, "api/v1/users/**").hasAnyRole(ADMIN_UPDATE.name(), USER_UPDATE.name())
                         .requestMatchers(POST, "api/v1/users/register").hasAnyRole(ADMIN_CREATE.name())
                         .requestMatchers(DELETE, "api/v1/users/**").hasAnyRole(ADMIN_DELETE.name())
                         .anyRequest()
                         .authenticated())
                 .httpBasic(Customizer.withDefaults())
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
    }

    @Bean
    protected AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }
}
