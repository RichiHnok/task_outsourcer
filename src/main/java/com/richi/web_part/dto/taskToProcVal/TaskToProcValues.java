package com.richi.web_part.dto.taskToProcVal;

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

    public List<TaskToProcValue> getValues() {
        return values;
    }

    public void setValues(List<TaskToProcValue> values) {
        this.values = values;
    }
}
