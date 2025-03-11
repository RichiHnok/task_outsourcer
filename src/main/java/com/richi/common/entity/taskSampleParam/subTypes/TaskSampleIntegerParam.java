package com.richi.common.entity.taskSampleParam.subTypes;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_sample_param_integer")
public class TaskSampleIntegerParam extends TaskSampleParam{
    
    @Column(name = "int_value")
    private Integer intValue = 0;

    public TaskSampleIntegerParam() {
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }
}
