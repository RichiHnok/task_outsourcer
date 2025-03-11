package com.richi.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;

public interface TaskSampleParamRepository extends JpaRepository<TaskSampleParam, Integer> {
    
}
