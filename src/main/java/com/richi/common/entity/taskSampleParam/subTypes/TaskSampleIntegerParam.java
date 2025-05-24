package com.richi.common.entity.taskSampleParam.subTypes;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.enums.TaskSampleParamType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_sample_param_integer")
public class TaskSampleIntegerParam extends TaskSampleParam{

    //? TODO Здесь должны прописываться ограничения для вводимых данных типа целое число
    private Long min;
    private Long max;

    public TaskSampleIntegerParam() {
        super(TaskSampleParamType.INTEGER);
    }

    public TaskSampleIntegerParam(
        Integer id
        , String name
        , String description
        , Long min
        , Long max
    ) {
        super(id, name, TaskSampleParamType.INTEGER, description);
        this.min = min;
        this.max = max;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "TaskSampleIntegerParam [min=" + min + ", max=" + max + "]";
    }
}
