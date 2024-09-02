package com.richi.richis_app.repository.user_rep;

import java.util.List;

import com.richi.richis_app.entity.User;

public interface UserRepository {
    
    public List<User> getAllUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deleteUser(int id);
}
