package com.richi.common.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.entity.taskToProc.TaskToProc;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "task_sample")
public class TaskSample {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_sample_id")
    private Integer id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    //? TODO Я не знаю, где надо хранить путь к скрипту. В БД или создать отдельный сервис, который будет генерировать путь? Пока пусть путь будет храниться в БД
    @Column(name = "script_path")
    private String scriptFilePath;

    //? TODO надо ли это делать так, чтобы админ мог настроить этот параметр доступа к серверу. условно сделать разные роли админов: admin-with-access-to-server и admin-without-access-to-server. И первый получается может любую строку здесь вводить, потому что у него есть понимание что есть на сервере, а второй будет собирать её через конструктор, потому что с ним обратная ситуация
    @Column(name = "launch_command_template")
    private String launchCommandTemplate;

    @Transient
    private MultipartFile scriptFile;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_sample_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<TaskSampleParam> params;

    @OneToMany(/* cascade = CascadeType.ALL, */ mappedBy = "taskSample", fetch = FetchType.EAGER)
    private List<TaskToProc> tasksToProc;

    public TaskSample() {
    }

    public TaskSample(
        Integer id
        , String name
        , String description
        , String launchCommandTemplate
        , MultipartFile scriptFile
        , String sciptFilePath
        , List<TaskSampleParam> params
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.launchCommandTemplate = launchCommandTemplate;
        this.scriptFile = scriptFile;
        this.scriptFilePath = sciptFilePath;
        this.params = params;
    }

    public void addParamToTaskSample(TaskSampleParam param){
        if(params == null){
            params = new ArrayList<>();
        }
        params.add(param);
    }

    public void removeParamFromTaskSample(int paramId, String paramName){
        if(params != null){
            if(paramId != 0){
                params.removeIf(obj -> obj.getId() == paramId);
            }else{
                params.removeIf(obj -> obj.getName().equals(paramName));
            }
        }
    }

    public void addTaskOfThisTaskSample(TaskToProc taskToProc){
        if(tasksToProc == null){
            tasksToProc = new ArrayList<>();
        }
        tasksToProc.add(taskToProc);
        taskToProc.setTaskSample(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TaskSampleParam> getParams() {
        return params;
    }

    public void setParams(List<TaskSampleParam> params) {
        this.params = params;
    }

    public String getScriptFilePath() {
        return scriptFilePath;
    }

    public void setScriptFilePath(String scriptFilePath) {
        this.scriptFilePath = scriptFilePath;
    }

    public MultipartFile getScriptFile() {
        return scriptFile;
    }

    public void setScriptFile(MultipartFile scriptFile) {
        this.scriptFile = scriptFile;
    }

    public String getLaunchCommandTemplate() {
        return launchCommandTemplate;
    }

    public void setLaunchCommandTemplate(String launchCommandTemplate) {
        this.launchCommandTemplate = launchCommandTemplate;
    }

    public List<TaskToProc> getTasksToProc() {
        return tasksToProc;
    }

    public void setTasksToProc(List<TaskToProc> tasksToProc) {
        this.tasksToProc = tasksToProc;
    }

    @Override
    public String toString() {
        return "TaskSample [id=" + id + ", name=" + name + ", description=" + description + ", params=" + params + "]";
    }  
}
