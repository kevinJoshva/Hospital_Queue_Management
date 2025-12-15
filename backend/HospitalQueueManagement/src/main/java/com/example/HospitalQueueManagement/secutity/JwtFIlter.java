package com.example.HospitalQueueManagement.secutity;


import com.example.HospitalQueueManagement.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFIlter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService service;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.equals("/auth/login")
                || path.equals("/auth/signup")
                || path.equals("/auth/signup/patient")
                || path.equals("/auth/signup/admin")
                ||path.equals("auth/signup/receptionist"))
        {

            filterChain.doFilter(request, response);
            return;
        }
            String header=request.getHeader("Authorization");
        String token = null;
        String email = null;
            if(header!=null&&header.startsWith("Bearer "))
            {
                token = header.substring(7);
                email = jwtUtil.extractEmail(token);
                if(email!=null&& SecurityContextHolder.getContext().getAuthentication()==null)
                {
                    UserDetails details=service.loadUserByUsername(email);
                    if(jwtUtil.validateToken(token,details))
                    {
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
        filterChain.doFilter(request, response);

    }
}
