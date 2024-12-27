package com.richi.common.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.User;
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

    public List<TaskToProc> geTaskToProcsByUser(User user){
        return taskToProcRepository.findByUserOrderByStartTimeDesc(user);
    }

    public TaskToProc getTaskToProc(int id) throws Exception {
        Optional<TaskToProc> taskToProc = taskToProcRepository.findById(id);
        if(taskToProc.isPresent()){
            return taskToProc.get();
        }else{
            throw new Exception("Cannot find task to proc by id: " + id);
        }
    }

    public TaskToProc saveTaskToProc(TaskToProc taskToProc) {
        return taskToProcRepository.save(taskToProc);
    }

    public void createWorkFoldersForTask(int taskToProcId) throws Exception{
        Path taskInputFolder = getInputFolderForTask(taskToProcId);
        if(!taskInputFolder.toFile().mkdirs()){
            throw new Exception("Troubles with creating input folder for task processing");
        }
        Path taskOutputFolder = getOutputFolderForTask(taskToProcId);
        if(!taskOutputFolder.toFile().mkdirs()){
            throw new Exception("Troubles with creating output folder for task processing");
        }
    }

    public Path getInputFolderForTask(int id) throws Exception {
        TaskToProc taskToProc = getTaskToProc(id);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmssddMMyyyy");
        Path userFolder = Paths.get("src\\main\\resources\\files\\users\\"
            , taskToProc.getUser().getLogin() 
            , Integer.toString(taskToProc.getTaskSample().getId())
            + taskToProc.getStartTime().format(dtf)
            , "input"
        );
        return userFolder;
    }

    public Path getOutputFolderForTask(int id) throws Exception {
        TaskToProc taskToProc = getTaskToProc(id);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmssddMMyyyy");
        Path userFolder = Paths.get("src\\main\\resources\\files\\users\\"
            , taskToProc.getUser().getLogin() 
            , Integer.toString(taskToProc.getTaskSample().getId())
            + taskToProc.getStartTime().format(dtf)
            , "output"
        );
        return userFolder;
    }
}
