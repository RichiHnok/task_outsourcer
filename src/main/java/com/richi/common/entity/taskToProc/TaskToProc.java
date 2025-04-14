package com.richi.common.entity.taskToProc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.richi.common.entity.TaskSample;
import com.richi.common.entity.User;
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
    private Date startTime;
    
    @Column(name = "params")
    private String taskParamsJson;

    @Transient
    private List<TaskToProcParam> taskParams;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskToProcStatus status = TaskToProcStatus.CREATED;


    //* Это для условия отображения ссылки на скачивание в шаблонизаторе
    @Transient
    @Deprecated
    private boolean isFinished = false;

    public TaskToProc() {
    }

    public TaskToProc(TaskSample taskSample, User user, Date startTime){
        this.taskSample = taskSample;
        this.user = user;
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<TaskToProcParam> convertTaskParamsToListFromJson() throws JsonMappingException, JsonProcessingException{
        if(taskParams == null){
            taskParams = new ArrayList<>();
        }
        if (this.taskParams.isEmpty() && this.taskParamsJson != null) {
            ObjectMapper mapper = new ObjectMapper();
            this.taskParams = mapper.readValue(this.taskParamsJson, new TypeReference<List<TaskToProcParam>>(){});
        }
        return taskParams;
    }

    public List<TaskToProcParam> getTaskParams() throws JsonMappingException, JsonProcessingException {
        return convertTaskParamsToListFromJson();
    }

    public void setTaskParams(List<TaskToProcParam> taskParams) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        this.taskParamsJson = mapper.writeValueAsString(taskParams);
        this.taskParams = taskParams;
    }

    public TaskToProcStatus getStatus() {
        return status;
    }

    public void setStatus(TaskToProcStatus status) {
        this.status = status;
    }

    @Deprecated
    public boolean isFinished() {
        return isFinished;
    }

    @Deprecated
    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public String toString() {
        return "TaskToProc [id=" + id + ", taskSample=" + taskSample.getName() + ", user=" + user.getLogin() + ", startTime=" + startTime
                + "]";
    }
}
