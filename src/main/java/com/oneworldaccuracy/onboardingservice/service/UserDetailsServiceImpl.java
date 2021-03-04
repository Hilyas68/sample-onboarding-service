package com.oneworldaccuracy.onboardingservice.service;

import com.oneworldaccuracy.onboardingservice.model.User;
import com.oneworldaccuracy.onboardingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User : " + email + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("user")));

    }
}
