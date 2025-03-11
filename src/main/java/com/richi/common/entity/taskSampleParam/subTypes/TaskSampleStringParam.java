package com.richi.common.entity.taskSampleParam.subTypes;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.enums.TaskSampleParamType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_sample_param_string")
public class TaskSampleStringParam extends TaskSampleParam{

    //? TODO Здесь должны прописываться ограничения для вводимых данных типа строка

    public TaskSampleStringParam() {
        super(TaskSampleParamType.STRING);
    }

    public TaskSampleStringParam(String paramName){
        super(paramName, TaskSampleParamType.STRING);
    }
}
