package com.anchuk.citylist.service;


import com.anchuk.citylist.model.dto.auth.CustomUserDetails;
import com.anchuk.citylist.model.entity.UserEntity;
import com.anchuk.citylist.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUsrDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public CustomUsrDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UserEntity user = userRepo.findByLoginIgnoreCase(login).orElseThrow(()
                -> new UsernameNotFoundException("User with email = " + login + " not exist!"));
        return new CustomUserDetails(user);
    }
}
