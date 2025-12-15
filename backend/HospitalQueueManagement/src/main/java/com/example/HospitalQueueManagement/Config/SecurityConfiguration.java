package com.example.HospitalQueueManagement.Config;


import com.example.HospitalQueueManagement.secutity.JwtFIlter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFIlter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http
                .csrf(c->c.disable())
                .authorizeHttpRequests(auth->auth

                        // PUBLIC AUTH ENDPOINTS
                        .requestMatchers(
                                "/auth/login",
                                "/auth/signup",
                                "/auth/signup/patient",
                                "/auth/signup/admin"
                        ).permitAll()

                        // ADMIN ONLY
                        .requestMatchers(
                                "/auth/signup/doctor",
                                "/auth/signup/receptionist"
                        ).hasRole("ADMIN")

                        // ROLE BASED APIs
                        .requestMatchers("/doctor/**").hasRole("DOCTOR")
                        .requestMatchers("/reception/**").hasRole("RECEPTIONIST")
                        .anyRequest().authenticated())
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
