package com.richi.richis_app.entity;

import java.util.ArrayList;
import java.util.List;

public class TaskValues {
    
    private List<String> values;

    public TaskValues() {
    }

    public TaskValues(TaskSample taskSample){
        values = new ArrayList<>();
        for(int i = 0; i < taskSample.getParams().size(); i++){
            values.add("0");
        }
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "TaskValues [values=" + values + "]";
    }
}
