package com.richi.web_part.controller;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.taskToProc.TaskToProc;
import com.richi.common.enums.TaskToProcStatus;
import com.richi.common.service.FileFolderManipulationService;
import com.richi.common.service.StorageService;
import com.richi.common.service.TaskSampleService;
import com.richi.common.service.TaskToProcService;
import com.richi.web_part.configuration.MyUserDetails;

@Controller
@RequestMapping("/download")
public class DownloadController {

    private final TaskSampleService taskSampleService;
    private final StorageService storageService;
    private final TaskToProcService taskToProcService;
    private final FileFolderManipulationService fileFolderManipulationService;

    public DownloadController(
        TaskSampleService taskSampleService
        , StorageService storageService
        , TaskToProcService taskToProcService
        , FileFolderManipulationService fileFolderManipulationService
    ){
        this.taskSampleService = taskSampleService;
        this.storageService = storageService;
        this.taskToProcService = taskToProcService;
        this.fileFolderManipulationService = fileFolderManipulationService;
    }
    
    @GetMapping("/taskSampleScript/{taskSampleId}")
    public ResponseEntity<Resource> serveTaskSampleScriptFilEntity(
        @PathVariable Integer taskSampleId
    ) throws Exception {

        TaskSample taskSample = taskSampleService.getTaskSample(taskSampleId);

        Path taskSampleScript = Path.of(taskSample.getScriptFilePath());
        Resource file = storageService.loadAsResource(taskSampleScript);

        return ResponseEntity.ok().header(
            HttpHeaders.CONTENT_DISPOSITION
            , "attachment; filename=\"" + file.getFilename() + "\""
        ).body(file);
    }
    
    @GetMapping("/taskToProc/{taskId}/result")
    public ResponseEntity<Resource> serveTaskResultFile(
        @PathVariable Integer taskId
		, @AuthenticationPrincipal UserDetails userDetails
    ) throws Exception {
		TaskToProc task = taskToProcService.getTaskToProc(taskId);
        if(task.getStatus() != TaskToProcStatus.FINISHED){
            throw new Exception("There is no result for this task");
        }
		if(task.getUser().getId() != ((MyUserDetails) userDetails).getUserId()){
			throw new AccessDeniedException("You are not owner of this file");
		}

		Path taskResultArchive = fileFolderManipulationService.getResultArchive(task);
		Resource file = storageService.loadAsResource(taskResultArchive);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=\"" + file.getFilename() + "\""
		).body(file);
	}
    
    @GetMapping("/taskToProc/{taskId}/fileParam")
    public ResponseEntity<Resource> serveTaskParamFile(
        @PathVariable Integer taskId
        , @RequestParam String fileParamName
        , @AuthenticationPrincipal UserDetails userDetails
    ) throws Exception{
        TaskToProc task = taskToProcService.getTaskToProc(taskId);

        if(task.getUser().getId() != ((MyUserDetails) userDetails).getUserId()){
			throw new AccessDeniedException("You are not owner of this file");
		}

        Path taskFileParamPath = fileFolderManipulationService.getFileParamOfTask(task, fileParamName);
        Resource file = storageService.loadAsResource(taskFileParamPath);

        if(file == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION
        , "attachment; filename=\"" + file.getFilename() + "\""
        ).body(file);

    }
}
