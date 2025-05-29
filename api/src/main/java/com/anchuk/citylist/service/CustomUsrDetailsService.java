package com.anchuk.citylist.service;


import com.anchuk.citylist.model.dto.auth.CustomUserDetails;
import com.anchuk.citylist.model.entity.UserEntity;
import com.anchuk.citylist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUsrDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UserEntity user = userRepo.findByLoginIgnoreCase(login).orElseThrow(()
                -> new UsernameNotFoundException("User with email = " + login + " not exist!"));
        return new CustomUserDetails(user);
    }
}
