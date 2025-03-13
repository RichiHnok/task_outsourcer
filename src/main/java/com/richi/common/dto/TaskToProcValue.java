package com.richi.common.dto;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;

public class TaskToProcValue{
        
    private TaskSampleParam param;
    private Object value;

    public TaskToProcValue() {
    }

    TaskToProcValue(TaskSampleParam param){
        this.param = param;
    }

    public TaskSampleParam getParam() {
        return param;
    }

    public void setParam(TaskSampleParam param) {
        this.param = param;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
