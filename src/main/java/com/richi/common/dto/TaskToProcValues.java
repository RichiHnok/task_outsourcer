package com.richi.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.taskSampleParam.TaskSampleParam;

public class TaskToProcValues{

    private List<TaskToProcValue> values;

    public TaskToProcValues() {
    }

    public TaskToProcValues(TaskSample taskSample){
        values = new ArrayList<>();
        for (TaskSampleParam param : taskSample.getParams()) {
            values.add(new TaskToProcValue(param));
        }
    }
    
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

    public List<TaskToProcValue> getValues() {
        return values;
    }

    public void setValues(List<TaskToProcValue> values) {
        this.values = values;
    }
}
