package com.richi.common.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.User;
import com.richi.common.enums.TaskToProcStatus;

public interface TaskToProcRepository extends JpaRepository<TaskToProc, Integer>{
    List<TaskToProc> findByUser(User user);
    List<TaskToProc> findByUserOrderByStartTimeDesc(User user);
    Page<TaskToProc> findByUserOrderByStartTimeDesc(User user, Pageable pageable);
    List<TaskToProc> findAllByOrderByStartTimeDesc();
    Page<TaskToProc> findAllByOrderByStartTimeDesc(Pageable pageable);
    TaskToProc findFirstByStatusOrderByUser_PriorityDescStartTimeDesc(TaskToProcStatus status);
    TaskToProc deleteById(int taskId);
}
