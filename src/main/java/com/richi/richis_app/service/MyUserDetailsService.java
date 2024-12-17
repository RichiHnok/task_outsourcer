package com.richi.richis_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.richi.richis_app.configuration.MyUserDetails;
import com.richi.richis_app.entity.User;
import com.richi.richis_app.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(login);

        return user.map(MyUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("There is no such user in REPO" + login));
    }
}
