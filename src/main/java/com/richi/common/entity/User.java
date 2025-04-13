package com.richi.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.richi.common.entity.taskToProc.TaskToProc;
import com.richi.common.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", name=" + name + ", surname=" + surname + ", userRole="
                + userRole + "]";
    }
}
