package com.richi.common.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.richi.common.entity.User;
import com.richi.common.entity.taskToProc.TaskToProc;
import com.richi.common.entity.taskToProc.TaskToProcParam;
import com.richi.common.enums.TaskSampleParamType;
import com.richi.common.enums.TaskToProcStatus;
import com.richi.common.repository.TaskToProcRepository;
import com.richi.web_part.dto.taskToProcVal.TaskToProcValueDto;
import com.richi.web_part.dto.taskToProcVal.TaskToProcValuesDto;

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

    public Page<TaskToProc> getTaskToProcsByUser(User user, Pageable pageable) throws Exception{
        Page<TaskToProc> tasks = taskToProcRepository.findByUserOrderByStartTimeDesc(user, pageable);
        for(TaskToProc task : tasks){
            task.convertTaskParamsToListFromJson();
        }
        return tasks;
    }

    public TaskToProc getTaskToProc(int id) throws Exception{
        TaskToProc task =taskToProcRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Cannot find task to proc by id: " + id)
        );
        task.convertTaskParamsToListFromJson();
        return task;
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

    public List<TaskToProcParam> saveValuesAndPutThemIntoTaskToProc(
        TaskToProc taskToProc
        , TaskToProcValuesDto taskToProcValues
    ) throws Exception{

        Path inputFolder = fileFolderManipulationService.getInputFolderForTask(taskToProc);

        List<TaskToProcParam> params = new ArrayList<>();

        if(taskToProcValues.getValues() != null){
            for(TaskToProcValueDto value : taskToProcValues.getValues()){
                if(value.getParam().getType() == TaskSampleParamType.FILE){
                    Path fileLocation = storageService.storeInFolder((MultipartFile) value.getValue(), inputFolder).toAbsolutePath();
                    
                    TaskToProcParam param = new TaskToProcParam(
                        value.getParam().getName()
                        , TaskSampleParamType.FILE
                        , fileLocation.toString()
                    );
                    params.add(param);
                }else{
                    String valueAsString = (String) value.getValue();

                    TaskToProcParam param = new TaskToProcParam(
                        value.getParam().getName()
                        , value.getParam().getType()
                        , valueAsString
                    );
                    params.add(param);
                }
            }
        }

        taskToProc.setTaskParams(params);
        return params;
    }
}
