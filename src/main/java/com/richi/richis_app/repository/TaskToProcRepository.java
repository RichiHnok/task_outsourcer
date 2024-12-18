package com.richi.richis_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.richi.richis_app.entity.TaskToProc;
import com.richi.richis_app.entity.User;

public interface TaskToProcRepository extends JpaRepository<TaskToProc, Integer>{
    List<TaskToProc> findByUser(User user);
    List<TaskToProc> findByUserOrderByStartTimeDesc(User user);
    List<TaskToProc> findAllByOrderByStartTimeDesc();
}
