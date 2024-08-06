package com.richi.richis_app.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "tasks_samples")
public class TaskSample {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_sample_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<TaskSampleParam> params;

    public TaskSample() {
    }

    public TaskSample(String name, String description) {
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return "TaskSample [id=" + id + ", name=" + name + ", description=" + description + ", params=" + params + "]";
    }  
}
