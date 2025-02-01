package com.richi.common.entity;

import java.net.URI;
import java.time.LocalDateTime;

import com.richi.common.enums.TaskToProcStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "task_to_proc")
public class TaskToProc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_to_proc_id")
    private Integer id;

    @ManyToOne(/* cascade = CascadeType.ALL */)
    @JoinColumn(name = "task_sample_id")
    private TaskSample taskSample;

    @ManyToOne(/* cascade = CascadeType.ALL */)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_time", updatable = false, nullable = false)
    private LocalDateTime startTime;

    @Column(name = "params")
    private String joinedParams;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskToProcStatus status = TaskToProcStatus.CREATED;

    @Transient
    private URI uriToDownloadResult;

    public TaskToProc() {
    }

    public TaskToProc(TaskSample taskSample, User user, LocalDateTime time, String params){
        this.taskSample = taskSample;
        this.user = user;
        this.startTime = time;
        this.joinedParams = params;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskSample getTaskSample() {
        return taskSample;
    }

    public void setTaskSample(TaskSample taskSample) {
        this.taskSample = taskSample;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getJoinedParams() {
        return joinedParams;
    }

    public void setJoinedParams(String joinedParams) {
        this.joinedParams = joinedParams;
    }

    public TaskToProcStatus getStatus() {
        return status;
    }

    public void setStatus(TaskToProcStatus status) {
        this.status = status;
    }

    public URI getUriToDownloadResult() {
        return uriToDownloadResult;
    }

    public void setUriToDownloadResult(URI uriToDownloadResult) {
        this.uriToDownloadResult = uriToDownloadResult;
    }

    @Override
    public String toString() {
        return "TaskToProc [id=" + id + ", taskSample=" + taskSample.getName() + ", user=" + user.getLogin() + ", startTime=" + startTime
                + ", joinedParams=" + joinedParams + "]";
    }
}
