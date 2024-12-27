package com.richi.common.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role"
        , joinColumns = @JoinColumn(name = "role_id")
        , inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> usersWithThisRole;

    public void addUserWithThisRole(User user){
        if(usersWithThisRole == null){
            usersWithThisRole = new ArrayList<>();
        }
        usersWithThisRole.add(user);
    }

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsersWithThisRole() {
        return usersWithThisRole;
    }

    public void setUsersWithThisRole(List<User> usersWithThisRole) {
        this.usersWithThisRole = usersWithThisRole;
    }

    @Override
    public String toString() {
        return "Role [roleId=" + roleId + ", roleName=" + roleName + "]";
    }    
}
