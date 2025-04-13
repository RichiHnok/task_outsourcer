package com.richi.web_part.dto.taskToProcVal;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;

public class TaskToProcValueDto{
        
    private TaskSampleParam param;
    private Object value;

    public TaskToProcValueDto() {
    }

    TaskToProcValueDto(TaskSampleParam param){
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
