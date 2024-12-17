package com.richi.richis_app.entity;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role"
        , joinColumns = @JoinColumn(name = "user_id")
        , inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> usersRoles;

    @OneToMany(/* cascade = CascadeType.ALL,  */mappedBy = "user", fetch = FetchType.EAGER)
    private List<TaskToProc> tasksToProc;

    @Column(name = "priority")
    private int priority = 5;

    @Column(name = "minutes_to_proc_task")
    private int minutesToProcessTask = 60;

    public User() {
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void addRoleToUser(Role role){
        if(usersRoles == null){
            usersRoles = new HashSet<>();
        }
        usersRoles.add(role);
    }

    public void addTaskOfThisUser(TaskToProc taskToProc){
        if(tasksToProc == null){
            tasksToProc = new ArrayList<>();
        }
        tasksToProc.add(taskToProc);
        taskToProc.setUser(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getMinutesToProcessTask() {
        return minutesToProcessTask;
    }

    public void setMinutesToProcessTask(int minutesToProcessTask) {
        this.minutesToProcessTask = minutesToProcessTask;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Role> getUsersRoles() {
        return usersRoles;
    }

    public void setUsersRoles(Set<Role> usersRoles) {
        this.usersRoles = usersRoles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TaskToProc> getTasksToProc() {
        return tasksToProc;
    }

    public void setTasksToProc(List<TaskToProc> tasksToProc) {
        this.tasksToProc = tasksToProc;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", name=" + name + ", surname=" + surname + ", email=" + email
                + ", usersRoles=" + usersRoles + "]";
    }
}
