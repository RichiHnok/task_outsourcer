package com.richi.richis_app.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "priority")
    private int priority = 5;

    @Column(name = "minutes_to_proc_task")
    private int minutesToProcessTask = 60;

    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // @JoinColumn(name = "ttp_user_id")
    // private List<TaskToProc> tasksToProc;

    public User() {
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public User(String name, String surname, int priority, int minutesToProcessTask) {
        this.name = name;
        this.surname = surname;
        this.priority = priority;
        this.minutesToProcessTask = minutesToProcessTask;
    }

    // public void addTaskToProcToUser(TaskToProc task){
    //     if(tasksToProc == null){
    //         tasksToProc = new ArrayList<>();
    //     }
    //     tasksToProc.add(task);
    //     // task.setUser(this);
    // }

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

    // public List<TaskToProc> getTasksToProc() {
    //     return tasksToProc;
    // }

    // public void setTasksToProc(List<TaskToProc> tasksToProc) {
    //     this.tasksToProc = tasksToProc;
    // }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", priority=" + priority
                + ", minutesToProcessTask=" + minutesToProcessTask + "]";
    }
    
}
