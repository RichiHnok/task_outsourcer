package com.richi.common.service;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.User;
import com.richi.common.enums.TaskToProcStatus;
import com.richi.common.repository.TaskToProcRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskToProcService {
    
    @Autowired private TaskToProcRepository taskToProcRepository;
    @Autowired private TaskToProcFilesService taskToProcFilesService;

    public TaskToProc deleteTaskToProc(int taskId) throws Exception{
        TaskToProc task = getTaskToProc(taskId);
        try {
            FileUtils.deleteDirectory(taskToProcFilesService.getWorkFolderForTask(task).toFile());
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception();
        }
        return taskToProcRepository.deleteById(taskId);
    }

    public List<TaskToProc> getAllTasksToProc() {
        return taskToProcRepository.findAllByOrderByStartTimeDesc();
    }

    public Page<TaskToProc> getAllTasksToProc(Pageable pageable){
        return taskToProcRepository.findAllByOrderByStartTimeDesc(pageable);
    }

    public List<TaskToProc> getTaskToProcsByUser(User user){
        return taskToProcRepository.findByUserOrderByStartTimeDesc(user);
    }

    public Page<TaskToProc> getTaskToProcsByUser(User user, Pageable pageable){
        return taskToProcRepository.findByUserOrderByStartTimeDesc(user, pageable);
    }

    public TaskToProc getTaskToProc(int id){
        return taskToProcRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Cannot find task to proc by id: " + id)
        );
    }

    //? TODO Что это и зачем я это создавал?
    public TaskToProc getTaskToProcByTopPriority(){
        return taskToProcRepository.findFirstByStatusOrderByUser_PriorityDescStartTimeDesc(TaskToProcStatus.CREATED);
    }

    public List<TaskToProc> getTaskWithStatuses(List<TaskToProcStatus> statuses){
        return taskToProcRepository.findAllByStatusIn(statuses);
    }

    public TaskToProc saveTaskToProc(TaskToProc taskToProc) {
        return taskToProcRepository.save(taskToProc);
    }

    public TaskToProc updateTaskStatus(TaskToProc task, TaskToProcStatus newStatus){
        task.setStatus(newStatus);
        return taskToProcRepository.save(task);
    }
}
