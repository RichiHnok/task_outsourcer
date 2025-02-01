package com.richi.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.richi.common.entity.TaskSample;

public interface TaskSampleRepository extends JpaRepository<TaskSample, Integer>{
    
}
