package com.richi.common.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.taskToProc.TaskToProc;
import com.richi.common.entity.taskToProc.TaskToProcParam;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FileFolderManipulationService {

    private final TaskSampleService taskSampleService;

    public FileFolderManipulationService(
        TaskSampleService taskSampleService
    ){
        this.taskSampleService = taskSampleService;
    }

    public Path getFolderForStoringTaskSampleScriptFile(TaskSample taskSample) {
        if(!taskSampleService.checkIfTaskSampleExists(taskSample.getId())){
            throw new EntityNotFoundException("There is no task sample with ID:: " + taskSample.getId());
        }
        StringBuilder relativePathBuilder = new StringBuilder();
        relativePathBuilder.append("src\\main\\resources\\files\\samples\\").append("sample"+taskSample.getId());
        return Path.of(relativePathBuilder.toString());
    }
    
    public void createInputOutputFoldersForTask(TaskToProc taskToProc) throws Exception{
        Path taskInputFolder = getInputFolderForTask(taskToProc);
        if(!taskInputFolder.toFile().mkdirs()){
            throw new Exception("Troubles with creating input folder for task processing");
        }
        Path taskOutputFolder = getOutputFolderForTask(taskToProc);
        if(!taskOutputFolder.toFile().mkdirs()){
            throw new Exception("Troubles with creating output folder for task processing");
        }
    }

    public Path getWorkFolderForTask(TaskToProc taskToProc) throws Exception {
        Path userFolder = Paths.get("src\\main\\resources\\files\\users\\"
            , taskToProc.getUser().getLogin() 
            , Integer.toString(taskToProc.getTaskSample().getId())
            + taskToProc.getId()
        );
        return userFolder;
    }

    public Path getInputFolderForTask(TaskToProc taskToProc) throws Exception {
        Path userFolder = Paths.get("src\\main\\resources\\files\\users\\"
            , taskToProc.getUser().getLogin() 
            , Integer.toString(taskToProc.getTaskSample().getId())
            + taskToProc.getId()
            , "input"
        );
        return userFolder;
    }

    public Path getOutputFolderForTask(TaskToProc taskToProc) throws Exception {
        Path userFolder = Paths.get("src\\main\\resources\\files\\users\\"
            , taskToProc.getUser().getLogin() 
            , Integer.toString(taskToProc.getTaskSample().getId())
            + Integer.toString(taskToProc.getId())
            , "output"
        );
        return userFolder;
    }

    //TODO Как будто отдельный аргумент для возврата названия с или без '.zip' выглядит не очень
    public String getNameForResultFile(TaskToProc task, boolean withZipEnding){
        DateFormat df = new SimpleDateFormat("HH-mm-ss-dd-MM-yyyy");
        StringBuilder resultArchiveName = new StringBuilder()
            .append("Result-")
            .append(task.getTaskSample().getId())
            .append("-")
            .append(task.getUser().getLogin())
            .append("-")
            .append(df.format(DateUtils.truncate(task.getStartTime(), Calendar.SECOND)));
        if(withZipEnding)
            resultArchiveName.append(".zip");
        return resultArchiveName.toString();
    }

    public Path getResultArchive(TaskToProc task) throws Exception{
        Path resultFilePath = getWorkFolderForTask(task).resolve(getNameForResultFile(task, true));
        return resultFilePath;
    }

    public Path getFileParamOfTask(TaskToProc task, String fileParamName) throws Exception{

        for(int i = 0, n = task.getTaskParams().size(); i < n; i++){
            TaskToProcParam param = task.getTaskParams().get(i);
            if(param.getParamName().equals(fileParamName)){
                Path fileParamPath = getInputFolderForTask(task).resolve(param.getParamValue());
                return fileParamPath;
            }
        }

        throw new Exception("This task dont have param with name: \"" + fileParamName + "\"");
    }
}
