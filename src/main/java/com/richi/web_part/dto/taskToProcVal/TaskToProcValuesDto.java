package com.richi.web_part.dto.taskToProcVal;

import java.util.ArrayList;
import java.util.List;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.taskSampleParam.TaskSampleParam;

public class TaskToProcValuesDto{

    private List<TaskToProcValueDto> values;

    public TaskToProcValuesDto() {
    }

    public TaskToProcValuesDto(TaskSample taskSample){
        values = new ArrayList<>();
        for (TaskSampleParam param : taskSample.getParams()) {
            values.add(new TaskToProcValueDto(param));
        }
    }

    public List<TaskToProcValueDto> getValues() {
        return values;
    }

    public void setValues(List<TaskToProcValueDto> values) {
        this.values = values;
    }
}
