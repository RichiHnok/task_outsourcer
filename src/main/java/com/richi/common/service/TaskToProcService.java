package com.richi.common.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.User;
import com.richi.common.enums.TaskToProcStatus;
import com.richi.common.repository.TaskToProcRepository;

@Service
public class TaskToProcService {
    
    @Autowired
    private TaskToProcRepository taskToProcRepository;

    public void deleteTaskToProc(int id) {
        taskToProcRepository.deleteById(id);
    }

    public List<TaskToProc> getAllTasksToProc() {
        return taskToProcRepository.findAllByOrderByStartTimeDesc();
    }

    public Page<TaskToProc> getAllTasksToProc(Pageable pageable){
        // List<TaskToProc> tasks = taskToProcRepository.findAllByOrderByStartTimeDesc();

        // int pageSize = pageable.getPageSize();
        // int currentPage = pageable.getPageNumber();
        // int startItem = currentPage * pageSize;

        // List<TaskToProc> list;

        // if(tasks.size() < startItem){
        //     list = Collections.emptyList();
        // }else{
        //     int toIndex = Math.min(startItem + pageSize, tasks.size());
        //     list = tasks.subList(startItem, toIndex);
        // }

        // Page<TaskToProc> taskPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), tasks.size());

        return taskToProcRepository.findAllByOrderByStartTimeDesc(pageable);
    }

    public List<TaskToProc> getTaskToProcsByUser(User user){
        // var tasks = taskToProcRepository.findByUserOrderByStartTimeDesc(user)
        //     .stream()
        //     .map(task -> {
        //         if(task.getStatus() == TaskToProcStatus.FINISHED)
        //             task.setUriToDownloadResult(null);
        //     });

        // return task;

        return taskToProcRepository.findByUserOrderByStartTimeDesc(user);
    }

    public Page<TaskToProc> getTaskToProcsByUser(User user, Pageable pageable){
        // List<TaskToProc> tasks = taskToProcRepository.findByUserOrderByStartTimeDesc(user);

        // int pageSize = pageable.getPageSize();
        // int currentPage = pageable.getPageNumber();
        // int startItem = currentPage * pageSize;

        // List<TaskToProc> list;

        // if(tasks.size() < startItem){
        //     list = Collections.emptyList();
        // }else{
        //     int toIndex = Math.min(startItem + pageSize, tasks.size());
        //     list = tasks.subList(startItem, toIndex);
        // }

        // Page<TaskToProc> taskPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), tasks.size());

        return taskToProcRepository.findByUserOrderByStartTimeDesc(user, pageable);
    }

    public TaskToProc getTaskToProc(int id) throws Exception {
        Optional<TaskToProc> taskToProc = taskToProcRepository.findById(id);
        if(taskToProc.isPresent()){
            return taskToProc.get();
        }else{
            throw new Exception("Cannot find task to proc by id: " + id);
        }
    }

    public TaskToProc getTaskToProcByTopPriority(){
        return taskToProcRepository.findFirstByStatusOrderByUser_PriorityDescStartTimeDesc(TaskToProcStatus.CREATED);
    }

    public TaskToProc saveTaskToProc(TaskToProc taskToProc) {
        return taskToProcRepository.save(taskToProc);
    }

    public TaskToProc updateTaskStatus(TaskToProc task, TaskToProcStatus newStatus){
        task.setStatus(newStatus);
        return taskToProcRepository.save(task);
    }
}
