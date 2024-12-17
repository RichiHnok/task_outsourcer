package com.richi.richis_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richi.richis_app.entity.TaskToProc;
import com.richi.richis_app.entity.User;
import com.richi.richis_app.repository.TaskToProcRepository;

@Service
public class TaskToProcService {
    
    @Autowired
    private TaskToProcRepository taskToProcRepository;

    public void deleteTaskToProc(int id) {
        taskToProcRepository.deleteById(id);
    }

    public List<TaskToProc> getAllTasksToProc() {
        return taskToProcRepository.findAll();
    }

    public List<TaskToProc> geTaskToProcsByUser(User user){
        return taskToProcRepository.findByUser(user);
    }

    public TaskToProc getTaskToProc(int id) throws Exception {
        Optional<TaskToProc> taskToProc = taskToProcRepository.findById(id);
        if(taskToProc.isPresent()){
            return taskToProc.get();
        }else{
            throw new Exception("Cannot find task to proc by id: " + id);
        }
    }

    public void saveTaskToProc(TaskToProc taskToProc) {
        taskToProcRepository.save(taskToProc);
    }
}
