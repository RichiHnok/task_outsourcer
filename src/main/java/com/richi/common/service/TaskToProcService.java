package com.richi.common.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.richi.common.dto.TaskToProcValues;
import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.User;
import com.richi.common.enums.TaskSampleParamType;
import com.richi.common.enums.TaskToProcStatus;
import com.richi.common.repository.TaskToProcRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskToProcService {
    
    private final TaskToProcRepository taskToProcRepository;
    private final FileFolderManipulationService fileFolderManipulationService;
    private final StorageService storageService;

    public TaskToProcService(
        TaskToProcRepository taskToProcRepository
        , FileFolderManipulationService fileFolderManipulationService
        , StorageService storageService
    ) {
        this.taskToProcRepository = taskToProcRepository;
        this.fileFolderManipulationService = fileFolderManipulationService;
        this.storageService = storageService;
    }

    public TaskToProc deleteTaskToProc(int taskId) throws Exception{
        TaskToProc task = getTaskToProc(taskId);
        try {
            FileUtils.deleteDirectory(fileFolderManipulationService.getWorkFolderForTask(task).toFile());
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

    public TaskToProc updateTaskStatus(
        TaskToProc task
        , TaskToProcStatus newStatus
    ){
        task.setStatus(newStatus);
        return taskToProcRepository.save(task);
    }

    public String saveValuesAndPutThemIntoTaskToProc(
        TaskToProc taskToProc
        , TaskToProcValues taskToProcValues
    ) throws Exception{

        Path inputFolder = fileFolderManipulationService.getInputFolderForTask(taskToProc);

        StringBuilder argumentsInStringBuilder = new StringBuilder();
        for(TaskToProcValues.TaskToProcValue value : taskToProcValues.getValues()){
            argumentsInStringBuilder.append(" \"");

            if(value.getParam().getType() == TaskSampleParamType.FILE){
                Path fileLocation = storageService.storeInFolder((MultipartFile) value.getValue(), inputFolder);
                argumentsInStringBuilder.append(fileLocation.toString());
            }else{
                String valueAsString = (String) value.getValue();
                argumentsInStringBuilder.append(valueAsString);
            }
            argumentsInStringBuilder.append("\"");
        }
        
        String endArgumentsString = argumentsInStringBuilder.toString().trim();
        taskToProc.setJoinedParams(endArgumentsString);
        return endArgumentsString;
    }
}
