package com.richi.richis_app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks_to_proc")
public class TaskToProc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "ttp_user_id")
    // private User user;

    // @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    // @JoinColumn(name = "ttp_task_sample_id")
    // private TaskSample taskSample;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "params")
    private String joinedParams;

        // private LocalDateTime endTime;
    
        // private TaskToProcStatus status;
        
    public TaskToProc() {
    }

    // public TaskToProc(User user, TaskSample taskSample, LocalDateTime startTime, String joinedParams) {
    //     this.user = user;
    //     this.taskSample = taskSample;
    //     this.startTime = startTime;
    //     this.joinedParams = joinedParams;
    // }

    public TaskToProc(LocalDateTime startTime, String joinedParams) {
        this.startTime = startTime;
        this.joinedParams = joinedParams;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }

    // public TaskSample getTaskSample() {
    //     return taskSample;
    // }

    // public void setTaskSample(TaskSample taskSample) {
    //     this.taskSample = taskSample;
    // }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getJoinedParams() {
        return joinedParams;
    }

    public void setJoinedParams(String params) {
        this.joinedParams = params;
    }

    // @Override
    // public String toString() {
    //     return "TaskToProc [id=" + id + ", user=" + user + ", taskSample=" + taskSample + ", startTime=" + startTime
    //             + ", params=" + joinedParams + "]";
    // }

    public List<String> getParamsAsList(){
        return new ArrayList<>(Arrays.asList(joinedParams.split("~")));
    }

    public void setParamsWithStringList(List<String> values){
        this.joinedParams = String.join("~", values);
    }

    public void setParamsWithTaskValues(TaskValues values){
        this.joinedParams = String.join("~", values.getValues());
    }
}
