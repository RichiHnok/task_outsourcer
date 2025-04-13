package com.richi.common.entity.taskToProc;

import com.richi.common.enums.TaskSampleParamType;

public class TaskToProcParam {

    private String paramName;

    private TaskSampleParamType paramType;

    private String paramValue;

    public TaskToProcParam() {
    }

    public TaskToProcParam(String paramName, TaskSampleParamType paramType, String paramValue) {
        this.paramName = paramName;
        this.paramType = paramType;
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public TaskSampleParamType getParamType() {
        return paramType;
    }

    public void setParamType(TaskSampleParamType paramType) {
        this.paramType = paramType;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "TP [pType=" + paramType + ", pVal=" + paramValue + "]";
    }
    
    
}
