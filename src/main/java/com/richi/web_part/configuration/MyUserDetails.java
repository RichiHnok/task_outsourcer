package com.richi.web_part.configuration;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.richi.common.entity.User;
import com.richi.common.enums.UserRole;

public class MyUserDetails implements UserDetails{
    
    private final Integer userId;
    private final String login;
    private final String firstname;
    private final String surname;
    private final String password;
    // private final String email;
    private final UserRole userRole;

    public MyUserDetails(User user){
        userId = user.getId();
        login = user.getLogin();
        firstname = user.getName();
        surname = user.getSurname();
        password = user.getPassword();
        // email = user.getEmail();
        userRole = user.getUserRole();
    }

    public Integer getUserId(){
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authoritiesList = new ArrayList<SimpleGrantedAuthority>();
        authoritiesList.add(new SimpleGrantedAuthority(userRole.name()));
        return authoritiesList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
