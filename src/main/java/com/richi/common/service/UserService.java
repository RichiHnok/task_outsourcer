package com.richi.common.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richi.common.entity.User;
import com.richi.common.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserRepository userRepository
        , PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByLogin(String login) throws NoSuchElementException{
        Optional<User> user = userRepository.findByLogin(login);

        return user.get();
        
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(int id) throws Exception{
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            return user.get();
        }else{
            throw new Exception("Can't get user");
        }
    }

    @Transactional
    public void saveUser(User user){
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(int userId){
        userRepository.deleteById(userId);
    }

    @Transactional
    public User registerUser(User user) throws Exception{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
