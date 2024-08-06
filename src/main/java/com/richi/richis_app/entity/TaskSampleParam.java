package com.richi.richis_app.entity;

import com.richi.richis_app.enums.TaskSampleParamType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks_params")
public class TaskSampleParam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated
    private TaskSampleParamType type = TaskSampleParamType.INTEGER;

    public TaskSampleParam() {
    }

    public TaskSampleParam(String name) {
        this.name = name;
    }

    public TaskSampleParam(String name, TaskSampleParamType type) {
        this.name = name;
        this.type = type;
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

    public TaskSampleParamType getType() {
        return type;
    }

    public void setType(TaskSampleParamType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TaskSampleParam [id=" + id + ", name=" + name + ", type=" + type + "]";
    }

    
}
