package com.example.HospitalQueueManagement.service;

import com.example.HospitalQueueManagement.model.Users;
import com.example.HospitalQueueManagement.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user=userRepo.findByEmail(email).orElseThrow(()->new  UsernameNotFoundException("User not found"));
       return new MyUserDetails(user);
    }
}
