package com.richi.richis_app.service.user_service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richi.richis_app.entity.User;
import com.richi.richis_app.repository.user_rep.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByLogin(String login) throws Exception{
        Optional<User> user = userRepository.findByLogin(login);

        if(user.isPresent()){
            return user.get();
        }else{
            throw new Exception("Не получилось получить пользователя");
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(int id) throws Exception{
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            return user.get();
        }else{
            throw new Exception("Не получилось получить пользователя");
        }
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(int userId){
        userRepository.deleteById(userId);
    }
}
