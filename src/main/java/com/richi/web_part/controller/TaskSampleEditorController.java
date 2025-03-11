package com.richi.web_part.controller;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.entity.taskSampleParam.subTypes.TaskSampleFileParam;
import com.richi.common.entity.taskSampleParam.subTypes.TaskSampleIntegerParam;
import com.richi.common.entity.taskSampleParam.subTypes.TaskSampleStringParam;
import com.richi.common.enums.TaskSampleParamType;
import com.richi.common.service.StorageService;
import com.richi.common.service.TaskSampleService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/editor")
public class TaskSampleEditorController {

    private Logger log = LoggerFactory.getLogger(TaskSampleEditorController.class);
    
    private final TaskSampleService taskSampleService;
    private final StorageService storageService;
    
    public TaskSampleEditorController(
        TaskSampleService taskSampleService
        , StorageService storageService
    ) {
        log.info("Creating task sample editor controller");
        this.taskSampleService = taskSampleService;
        this.storageService = storageService;
    }


    @GetMapping("/taskSamples")
    public String showAllTaskSamples(
        Model model
    ){
        List<TaskSample> allTaskSamples = taskSampleService.getAllTaskSamples();
        model.addAttribute("taskSamples", allTaskSamples);
        return "editor/task-sample/task-samples-editor";
    }

    @GetMapping("/addTaskSample")
    public String addNewTaskSample(
        Model model
    ){
        TaskSample taskSample = new TaskSample();
        model.addAttribute("taskSample", taskSample);
        return "editor/task-sample/task-sample-info";
    }

    @PostMapping(value = "/editTaskSample", params = "save")
    public String saveTaskSample(
        @ModelAttribute TaskSample taskSample
    ) throws Exception{
        //TODO При заугрузке скрипта, переименовывать его используя название шаблона
        //TODO при изменении параметров удалять старый шаблон и создавать новый, а не изменять старый, чтобы в базе данных в таблице task_to_proc_не былопутаницы с параметрами

        try {
            TaskSample currentTaskSampleInDB = taskSampleService.getTaskSample(
                taskSample.getId()
            );
            String olderScriptPath = currentTaskSampleInDB.getScriptFilePath();
            if(!taskSample.getScriptFile().isEmpty() && olderScriptPath != null){
                Path olderFilePath = Path.of(olderScriptPath);
                storageService.deleteFile(olderFilePath);
            }

            if(!taskSample.getScriptFile().isEmpty()){
                //TODO стльно торопился поэтому пришлось наговнокодить сохранение файла и запись пути сохранения в базу. Надо поправить
                Path relativePathToStore = storageService.storeInFolder(
                    taskSample.getScriptFile()
                    , taskSampleService.getFolderForStoringScriptFile(taskSample)
                );
                taskSample.setScriptFilePath(relativePathToStore.toString());
            }else{
                taskSample.setScriptFilePath(olderScriptPath);
            }
        } catch (EntityNotFoundException e) {
            taskSample = taskSampleService.saveTaskSample(taskSample);
            if(!taskSample.getScriptFile().isEmpty()){
                Path relativePathToStore = storageService.storeInFolder(
                    taskSample.getScriptFile()
                    , taskSampleService.getFolderForStoringScriptFile(taskSample)
                );
                taskSample.setScriptFilePath(relativePathToStore.toString());
            }
            
        }
        
        taskSampleService.saveTaskSample(taskSample);
        return "redirect:/editor/taskSamples";
    }

    @PostMapping(value = "/editTaskSample", params = "addParam")
    public String addParamToTaskSample(
        @ModelAttribute("taskSample") TaskSample taskSample
        // , @RequestParam("fileUrl") String fileUrl
        , @RequestParam(name = "fileName", required = false) String fileName
        , HttpServletRequest request
        , Model model
    ) throws Exception{
        //TODO при добавлении/удаления параметра и страница обновляется исчезает информация о прикреплённом скрипте
        String paramName = "";
        
        //? TODO Это выглядит мега костыльно
        while(true){
            paramName = "No name " + (Integer.toString(1000 + (int)(ThreadLocalRandom.current().nextDouble() * ((9999 - 1000) + 1))));
            if(taskSample.getParams() == null || !taskSample.getParams().stream().map(TaskSampleParam::getName).collect(Collectors.toList()).contains(paramName)){
                break;
            }
        }
        TaskSampleParamType typeOfNewParam = TaskSampleParamType.valueOf(request.getParameter("addingType"));
        
        TaskSampleParam param;
        //? TODO Делать что-то черех свитч это как будто такое себе и по-хорошему это надо всё через рефлексию сделать, но...
        switch (typeOfNewParam) {
            case TaskSampleParamType.INTEGER:
                param = new TaskSampleIntegerParam(paramName);
                break;
            case TaskSampleParamType.STRING:
                param = new TaskSampleStringParam(paramName);
                break;
            case TaskSampleParamType.FILE:
                param = new TaskSampleFileParam(paramName);
                break;
            default:
                throw new Exception("Problems with creating task sample param");
        }

        
        taskSample.addParamToTaskSample(param);
        model.addAttribute("taskSample", taskSample);
        // model.addAttribute("file", fileUrl);
        model.addAttribute("fileName", fileName);
        return "editor/task-sample/task-sample-info";
    }

    // @RequestMapping(value = "/saveTaskSample", method = RequestMethod.POST, params = "removeParam")
    // public String removeParamFromTaskSample(@ModelAttribute("taskSample") TaskSample taskSample, Model model){
    //     TaskSampleParam param = new TaskSampleParam("No name");
    //     taskSample.addParamToTaskSample(param);
    //     model.addAttribute("taskSample", taskSample);
    //     return "editor/task-sample/task-sample-info";
    // }

    //? TODO Как по человечески передавать несколько параметров в поле value в html странице
    @PostMapping(value = "/editTaskSample", params = "removeParam")
    public String removeParamFromTaskSample(
        Model model
        , @RequestParam("taskSampleParamId") int paramId
        , @RequestParam("taskSampleParamName") String paramName
        , @RequestParam(name = "fileName", required = false) String fileName
        , @ModelAttribute TaskSample taskSample
    ){
        taskSample.removeParamFromTaskSample(paramId, paramName);
        model.addAttribute("taskSample", taskSample);
        model.addAttribute("fileName", fileName);
        return "editor/task-sample/task-sample-info";
    }

    @GetMapping("/updateTaskSample/{taskSampleId}")
    public String updateTaskSample(
        @PathVariable("taskSampleId") Integer taskSampleId
        , Model model
    ){
        TaskSample taskSample = taskSampleService.getTaskSample(taskSampleId);

        if(taskSample.getScriptFilePath() != null){
            // Path scriptLocationPath = Path.of(taskSample.getScriptFilePath());
            
            model.addAttribute("fileName", Path.of(taskSample.getScriptFilePath()).getFileName());
        }

        model.addAttribute("taskSample", taskSample);
        return "editor/task-sample/task-sample-info";
    }

    @RequestMapping("/deleteInfo/taskSample")
    public String deleteTaskSample(
        @RequestParam("taskSampleId") int id
    ){
        //TODO Сделать удаление в виде изменения статуса (доп. поля) на "REMOVED". Это нужно для того, чтобы при просмотре tasksToProc не пропадала информация о пользователе.
        taskSampleService.deleteTaskSampleById(id);
        return "redirect:/editor/taskSamples";
    }
}
