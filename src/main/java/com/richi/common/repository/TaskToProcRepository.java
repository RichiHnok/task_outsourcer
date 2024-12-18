package com.richi.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.User;

public interface TaskToProcRepository extends JpaRepository<TaskToProc, Integer>{
    List<TaskToProc> findByUser(User user);
    List<TaskToProc> findByUserOrderByStartTimeDesc(User user);
    List<TaskToProc> findAllByOrderByStartTimeDesc();
}
