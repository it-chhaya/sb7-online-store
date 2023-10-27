package com.devkh.onlinestore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // Create admin
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("12345"))
                .roles("ADMIN")
                .build();

        // Create staff
        UserDetails staff = User.withUsername("staff")
                .password(passwordEncoder.encode("12345"))
                .roles("STAFF")
                .build();

        // Create customer
        UserDetails customer = User.withUsername("customer")
                .password(passwordEncoder.encode("12345"))
                .roles("CUSTOMER")
                .build();

        // Add users into in-memory user manager
        manager.createUser(admin);
        manager.createUser(staff);
        manager.createUser(customer);

        return manager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // TODO: What you want to customize
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/categories/**").hasAnyRole("STAFF", "ADMIN")
                .anyRequest().authenticated());

        // TODO: Use default form login
        http.formLogin(Customizer.withDefaults());

        // TODO: Configure HTTP Basic for Client Application. Example: Postman, Insomnia, ...
        http.httpBasic(Customizer.withDefaults());
        http.csrf(token -> token.disable());

        // TODO: Update API policy to STATELESS
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
