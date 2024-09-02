package com.richi.richis_app.service.user_service;

import java.util.List;

import com.richi.richis_app.entity.User;

public interface UserService {
    
    public List<User> getAllUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deleteUser(int id);
}
