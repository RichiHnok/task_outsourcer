package com.richi.common.entity.taskSampleParam.subTypes;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_sample_param_string")
public class TaskSampleStringParam extends TaskSampleParam{
    
    @Column(name = "str_value")
    private String strValue;

    public TaskSampleStringParam() {
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }
}
