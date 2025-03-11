package com.richi.common.entity.taskSampleParam.subTypes;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.enums.TaskSampleParamType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_sample_param_integer")
public class TaskSampleIntegerParam extends TaskSampleParam{

    //? TODO Здесь должны прописываться ограничения для вводимых данных типа целое число

    public TaskSampleIntegerParam() {
        super(TaskSampleParamType.INTEGER);
    }

    public TaskSampleIntegerParam(String paramName){
        super(paramName, TaskSampleParamType.INTEGER);
    }
}
