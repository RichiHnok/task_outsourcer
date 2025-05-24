package com.richi.common.entity.taskSampleParam;

import com.richi.common.enums.TaskSampleParamType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_sample_param")
@Inheritance(strategy = InheritanceType.JOINED)
public class TaskSampleParam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_sample_param_id")
    private Integer id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated
    private TaskSampleParamType type;

    @Column(name = "description")
    private String description;

    public TaskSampleParam() {
    }

    public TaskSampleParam(String name) {
        this.name = name;
    }

    public TaskSampleParam(TaskSampleParamType type) {
        this.type = type;
    }

    public TaskSampleParam(
        Integer id
        , String name
        , TaskSampleParamType type
        , String description
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
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

    public TaskSampleParamType getType() {
        return type;
    }

    public void setType(TaskSampleParamType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskSampleParam [id=" + id + ", name=" + name + ", type=" + type + "]";
    }

    
}
