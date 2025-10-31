package com.example.employee_management.config;

import com.example.employee_management.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/employees", "/api/v1/employees/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/employees/add", "/employees/save", "/employees/edit/**", "/employees/delete/**", "/employees/statistics").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/employees/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/employees/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/employees/**").hasAuthority("ADMIN")
                .requestMatchers("/register", "/register/save").permitAll()
                .requestMatchers("/hello").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(withDefaults())
            .logout(logout -> logout.permitAll());

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}

