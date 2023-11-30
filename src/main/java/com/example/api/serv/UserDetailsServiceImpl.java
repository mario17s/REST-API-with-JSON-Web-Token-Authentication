package com.example.api.serv;

import com.example.api.model.User;
import com.example.api.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = repo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User does not exist with email "+email));
        UserDetails userDetails = UserDetailsImpl.build(userEntity);
        return userDetails;
    }
}
