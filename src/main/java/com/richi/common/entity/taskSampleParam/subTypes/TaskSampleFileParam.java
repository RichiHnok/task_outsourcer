package com.richi.common.entity.taskSampleParam.subTypes;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.enums.TaskSampleParamType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_sample_param_file")
public class TaskSampleFileParam extends TaskSampleParam{

    //? TODO Здесь должны прописываться ограничения для вводимых данных типа файл

    public TaskSampleFileParam() {
        super(TaskSampleParamType.FILE);
    }

    public TaskSampleFileParam(
        Integer id
        , String paramName
        , String description
    ) {
        super(id, paramName, TaskSampleParamType.FILE, description);
    }
}
