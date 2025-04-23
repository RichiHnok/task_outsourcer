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
import com.richi.common.service.FileFolderManipulationService;
import com.richi.common.service.StorageService;
import com.richi.common.service.TaskSampleService;
import com.richi.web_part.dto.editingTaskSample.EditingTaskSampleDto;
import com.richi.web_part.dto.editingTaskSample.taskSampleParam.TaskSampleParamMainInfoDto;
import com.richi.web_part.dto.taskSamplesEditor.TaskSamplesEditorDto;
import com.richi.web_part.mapper.TemporaryMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/editor/taskSamples")
public class TaskSampleEditorController {

    private Logger log = LoggerFactory.getLogger(TaskSampleEditorController.class);
    
    private final TaskSampleService taskSampleService;
    private final StorageService storageService;
    private final FileFolderManipulationService fileFolderManipulationService;
    private final TemporaryMapper mapper;
    
    public TaskSampleEditorController(
        TaskSampleService taskSampleService
        , StorageService storageService
        , FileFolderManipulationService fileFolderManipulationService
        , TemporaryMapper mapper
    ) {
        log.info("Creating task sample editor controller");
        this.taskSampleService = taskSampleService;
        this.storageService = storageService;
        this.fileFolderManipulationService = fileFolderManipulationService;
        this.mapper = mapper;
    }


    @GetMapping
    public String showAllTaskSamples(
        Model model
    ){
        List<TaskSample> allTaskSamples = taskSampleService.getAllTaskSamples();
        TaskSamplesEditorDto taskSamplesEditorDto = mapper.createTaskSamplesEditorDto(allTaskSamples);
        model.addAttribute("taskSamplesEditorDto", taskSamplesEditorDto);
        return "editor/task-sample/task-samples-editor";
    }

    @GetMapping("/add")
    public String addNewTaskSample(
        Model model
    ){
        EditingTaskSampleDto editingTaskSampleDto = mapper.createAddingTaskSampleDto();
        model.addAttribute("editingTaskSampleDto", editingTaskSampleDto);
        // model.addAttribute("taskSample", taskSample);
        return "editor/task-sample/task-sample-info";
    }

    @PostMapping(value = "/edit", params = "save")
    public String saveTaskSample(
        @ModelAttribute("editingTaskSampleDto") EditingTaskSampleDto editingTaskSampleDto
    ) throws Exception{
        // // TODO При заугрузке скрипта, переименовывать его используя название шаблона
        // // TODO при изменении параметров удалять старый шаблон и создавать новый, а не изменять старый, чтобы в базе данных в таблице task_to_proc_не былопутаницы с параметрами

        // try {
        //     TaskSample currentTaskSampleInDB = taskSampleService.getTaskSample(
        //         taskSample.getId()
        //     );
        //     String olderScriptPath = currentTaskSampleInDB.getScriptFilePath();
        //     if(!taskSample.getScriptFile().isEmpty() && olderScriptPath != null){
        //         Path olderFilePath = Path.of(olderScriptPath);
        //         storageService.deleteFile(olderFilePath);
        //     }

        //     if(!taskSample.getScriptFile().isEmpty()){
        //         // TODO стльно торопился поэтому пришлось на кодить сохранение файла и запись пути сохранения в базу. Надо поправить
        //         Path relativePathToStore = storageService.storeInFolder(
        //             taskSample.getScriptFile()
        //             , fileFolderManipulationService.getFolderForStoringTaskSampleScriptFile(taskSample)
        //         );
        //         taskSample.setScriptFilePath(relativePathToStore.toString());
        //     }else{
        //         taskSample.setScriptFilePath(olderScriptPath);
        //     }
        // } catch (EntityNotFoundException e) {
        //     taskSample = taskSampleService.saveTaskSample(taskSample);
        //     if(!taskSample.getScriptFile().isEmpty()){
        //         Path relativePathToStore = storageService.storeInFolder(
        //             taskSample.getScriptFile()
        //             , fileFolderManipulationService.getFolderForStoringTaskSampleScriptFile(taskSample)
        //         );
        //         taskSample.setScriptFilePath(relativePathToStore.toString());
        //     }
            
        // }
        
        // taskSampleService.saveTaskSample(taskSample);
        taskSampleService.saveTaskSample(mapper.getTaskSampleFromEditingTaskSampleDto(editingTaskSampleDto));
        return "redirect:/editor/taskSamples";
    }

    @PostMapping(value = "/edit", params = "addParam")
    public String addParamToTaskSample(
        @ModelAttribute("editingTaskSampleDto") EditingTaskSampleDto editingTaskSampleDto
        // , @RequestParam(name = "fileName", required = false) String fileName
        , HttpServletRequest request
        , Model model
    ) throws Exception{

        // // TODO при добавлении/удаления параметра и страница обновляется исчезает информация о прикреплённом скрипте
        // String paramName = "";
        
        // // ? TODO Это выглядит мега костыльно
        // while(true){
        //     paramName = "No name " + (Integer.toString(1000 + (int)(ThreadLocalRandom.current().nextDouble() * ((9999 - 1000) + 1))));
        //     if(editingTaskSampleDto.params() == null || !editingTaskSampleDto.params().stream().map(TaskSampleParamDto::getName).collect(Collectors.toList()).contains(paramName)){
        //         break;
        //     }
        // }
        // TaskSampleParamType typeOfNewParam = TaskSampleParamType.valueOf(request.getParameter("addingType"));
        
        // TaskSampleParam param =null;
        // // ? TODO Делать что-то черех свитч это как будто такое себе и по-хорошему это надо всё через рефлексию сделать, но...
        // switch (typeOfNewParam) {
        //     case TaskSampleParamType.INTEGER:
        //         param = new TaskSampleIntegerParam(paramName, 0L, 0L);
        //         break;
        //     case TaskSampleParamType.STRING:
        //         param = new TaskSampleStringParam(paramName);
        //         break;
        //     case TaskSampleParamType.FILE:
        //         param = new TaskSampleFileParam(paramName);
        //         break;
        //     default:
        //         throw new Exception("Problems with creating task sample param");
        // }

        // // TODO Не надо получать TaskSample из БД
        // TaskSample taskSample = taskSampleService.getTaskSample(editingTaskSampleDto.id());
        // taskSample.addParamToTaskSample(param);
        editingTaskSampleDto = mapper.addParamToTaskSample(
            editingTaskSampleDto
            , TaskSampleParamType.valueOf(request.getParameter("addingType"))
        );
        model.addAttribute("editingTaskSampleDto", editingTaskSampleDto);
        // model.addAttribute("taskSample", taskSample);
        
        // model.addAttribute("fileName", fileName);
        return "editor/task-sample/task-sample-info";
    }

    //? TODO Как по человечески передавать несколько параметров в поле value в html странице
    @PostMapping(value = "/edit", params = "removeParam")
    public String removeParamFromTaskSample(
        Model model
        , @RequestParam("paramUuid") String paramUuid
        // , @ModelAttribute TaskSample taskSample
        , @ModelAttribute EditingTaskSampleDto editingTaskSampleDto
    ) throws Exception{
        editingTaskSampleDto = mapper.removeParamFromTaskSample(
            editingTaskSampleDto
            , paramUuid
        );
        model.addAttribute("editingTaskSampleDto", editingTaskSampleDto);

        // taskSample.removeParamFromTaskSample(paramId, paramName);
        // model.addAttribute("taskSample", taskSample);
        // model.addAttribute("fileName", fileName);
        return "editor/task-sample/task-sample-info";
    }

    @GetMapping("/update/{taskSampleId}")
    public String updateTaskSample(
        @PathVariable("taskSampleId") Integer taskSampleId
        , Model model
    ){
        
        TaskSample taskSample = taskSampleService.getTaskSample(taskSampleId);

        EditingTaskSampleDto editingTaskSampleDto = mapper.createEditingTaskSampleDto(taskSample);
        model.addAttribute("editingTaskSampleDto", editingTaskSampleDto);
        // if(taskSample.getScriptFilePath() != null){
        //     // Path scriptLocationPath = Path.of(taskSample.getScriptFilePath());
            
        //     model.addAttribute("fileName", Path.of(taskSample.getScriptFilePath()).getFileName());
        // }
        // model.addAttribute("taskSample", taskSample);
        return "editor/task-sample/task-sample-info";
    }

    @RequestMapping("/delete/{taskSampleId}")
    public String deleteTaskSample(
        @PathVariable("taskSampleId") Integer id
    ){
        //TODO Сделать удаление в виде изменения статуса (доп. поля) на "REMOVED". Это нужно для того, чтобы при просмотре tasksToProc не пропадала информация о пользователе.
        taskSampleService.deleteTaskSampleById(id);
        return "redirect:/editor/taskSamples";
    }
}
