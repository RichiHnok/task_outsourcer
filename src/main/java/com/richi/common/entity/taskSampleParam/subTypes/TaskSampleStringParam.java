package com.richi.common.entity.taskSampleParam.subTypes;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.enums.TaskSampleParamType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_sample_param_string")
public class TaskSampleStringParam extends TaskSampleParam{

    //? TODO Здесь должны прописываться параметры связанные с ограничениями для вводимых данных типа строка
    @Column(name = "regex_constraint")
    private String regExConstraint;
    @Column(name = "hint_value")
    private String hintValue;

    public TaskSampleStringParam() {
        super(TaskSampleParamType.STRING);
    }

    public TaskSampleStringParam(
        Integer id    
        , String paramName
        , String description
        , String regExConstraint
        , String hintValue
    ){
        super(id, paramName, TaskSampleParamType.STRING, description);
        this.regExConstraint = regExConstraint;
        this.hintValue = hintValue;
    }

    public String getRegExConstraint() {
        return regExConstraint;
    }

    public void setRegExConstraint(String regExConstraint) {
        this.regExConstraint = regExConstraint;
    }

    public String getHintValue() {
        return hintValue;
    }

    public void setHintValue(String hintValue) {
        this.hintValue = hintValue;
    }
}
