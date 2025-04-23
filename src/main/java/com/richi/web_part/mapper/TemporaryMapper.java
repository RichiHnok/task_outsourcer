package com.richi.web_part.mapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import com.richi.common.entity.TaskSample;
import com.richi.common.entity.User;
import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.entity.taskSampleParam.subTypes.TaskSampleFileParam;
import com.richi.common.entity.taskSampleParam.subTypes.TaskSampleIntegerParam;
import com.richi.common.entity.taskSampleParam.subTypes.TaskSampleStringParam;
import com.richi.common.entity.taskToProc.TaskToProc;
import com.richi.common.entity.taskToProc.TaskToProcParam;
import com.richi.common.enums.TaskSampleParamType;
import com.richi.common.enums.TaskToProcStatus;
import com.richi.common.service.TaskSampleService;
import com.richi.common.service.TaskToProcService;
import com.richi.web_part.dto.commonDto.TaskParamInfoDto;
import com.richi.web_part.dto.controlPanel.ControlPanelDto;
import com.richi.web_part.dto.controlPanel.TaskInfoForControlPanelDto;
import com.richi.web_part.dto.editingTaskSample.EditingTaskSampleDto;
import com.richi.web_part.dto.editingTaskSample.taskSampleParam.TaskSampleParamMainInfoDto;
import com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.FileParamConstraintsDto;
import com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.IntegerParamConstraintsDto;
import com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.StringParamConstraintsDto;
import com.richi.web_part.dto.launchingTask.LaunchingTaskDto;
import com.richi.web_part.dto.launchingTask.TaskToProcValueDto;
import com.richi.web_part.dto.personalCabinet.PersonalCabinetDto;
import com.richi.web_part.dto.personalCabinet.TaskInfoForPersonalCabinetDto;
import com.richi.web_part.dto.taskProcessingLaunched.TaskParamWithValueDto;
import com.richi.web_part.dto.taskProcessingLaunched.TaskProcessingLaunchedDto;
import com.richi.web_part.dto.taskSamplesEditor.TaskSampleInfoShowedInEditorDto;
import com.richi.web_part.dto.taskSamplesEditor.TaskSamplesEditorDto;

// TODO Пока что это будет общий маппер для всех ДТО-х. Когда разрастётся, разбить на более мелкие мапперы
@Service
public class TemporaryMapper {

    private final LocaleChangeInterceptor localeChangeInterceptor;

    private final UserDetailsService userDetailsService;

    private final TaskToProcService taskToProcService;
    private final TaskSampleService taskSampleService;
    
    public TemporaryMapper(
        TaskToProcService taskToProcService
        , UserDetailsService userDetailsService
        , LocaleChangeInterceptor localeChangeInterceptor
        , TaskSampleService taskSampleService
    ) {
        this.taskToProcService = taskToProcService;
        this.userDetailsService = userDetailsService;
        this.localeChangeInterceptor = localeChangeInterceptor;
        this.taskSampleService = taskSampleService;
    }

    public PersonalCabinetDto createPersonalCabinetDto(
        User user
        , Pageable pageable
    ) throws Exception{
        
        Page<TaskToProc> tasks = taskToProcService.getTaskToProcsByUser(
            user
			, pageable
        );
        
        List<TaskInfoForPersonalCabinetDto> tasksInfo = new ArrayList<>();

        for(TaskToProc task : tasks){

            List<TaskParamInfoDto> taskParams = new ArrayList<>();
            for(TaskToProcParam taskParam : task.getTaskParams()){
                taskParams.add(new TaskParamInfoDto(
                    taskParam.getParamType()
                    , taskParam.getParamName()
                    , (taskParam.getParamType() != TaskSampleParamType.FILE) ?
                        taskParam.getParamValue() :
                        Paths.get(taskParam.getParamValue()).getFileName().toString()
                ));
            }

            tasksInfo.add(new TaskInfoForPersonalCabinetDto(
                task.getId()
                , task.getStartTime()
                , task.getTaskSample().getName()
                , task.getStatus()
                , (task.getStatus() == TaskToProcStatus.FINISHED) ? true : false
                , taskParams
            ));
        }

        Page<TaskInfoForPersonalCabinetDto> pageOfTasks = new PageImpl<>(tasksInfo, pageable, tasksInfo.size());

        int totalPages = pageOfTasks.getTotalPages();
        List<Integer> pageNumbers = null;
		if(totalPages > 0){
			pageNumbers = IntStream.rangeClosed(1, totalPages)
				.boxed()
				.toList();
		}

        return new PersonalCabinetDto(
            user.getName()
            , user.getSurname()
            , pageOfTasks
            , pageNumbers
        );
    }

    public ControlPanelDto createControlPanelDto(
        Pageable pageable
    ) throws Exception{

        Page<TaskToProc> tasks = taskToProcService.getAllTasksToProc(pageable);

        List<TaskInfoForControlPanelDto> tasksInfo = new ArrayList<>();

        for(TaskToProc task : tasks){

            List<TaskParamInfoDto> taskParams = new ArrayList<>();
            for(TaskToProcParam taskParam : task.getTaskParams()){
                taskParams.add(new TaskParamInfoDto(
                    taskParam.getParamType()
                    , taskParam.getParamName()
                    , (taskParam.getParamType() != TaskSampleParamType.FILE) ?
                        taskParam.getParamValue() :
                        Paths.get(taskParam.getParamValue()).getFileName().toString()
                ));
            }

            tasksInfo.add(new TaskInfoForControlPanelDto(
                task.getId()
                , task.getStartTime()
                , task.getUser().getLogin()
                , task.getTaskSample().getName()
                , taskParams
                , task.getStatus()
            ));
        }

        Page<TaskInfoForControlPanelDto> pageOfTasks = new PageImpl<>(tasksInfo, pageable, tasksInfo.size());

        int totalPages = pageOfTasks.getTotalPages();
        List<Integer> pageNumbers = null;
		if(totalPages > 0){
			pageNumbers = IntStream.rangeClosed(1, totalPages)
				.boxed()
				.toList();
		}

        return new ControlPanelDto(
            pageOfTasks   
            , pageNumbers
        );
    }

    public LaunchingTaskDto createLaunchingTaskDto(
        TaskSample taskSample
    ) throws Exception{

        List<TaskToProcValueDto> taskValues = new ArrayList<>();

        for(TaskSampleParam taskSampleParam : taskSample.getParams()){
            taskValues.add(new TaskToProcValueDto(
                taskSampleParam.getId()
                , taskSampleParam.getName()
                , taskSampleParam.getType()
                , null
            ));
        }

        return new LaunchingTaskDto(
            taskSample.getId()
            , taskSample.getName()
            , taskSample.getDescription()
            , taskValues
        );
    }

    public TaskProcessingLaunchedDto createTaskProcessingLaunchedDto(
        TaskSample taskSample
        , TaskToProc task
    ) throws Exception{

        List<TaskParamWithValueDto> taskValues = new ArrayList<>();

        for(TaskToProcParam taskParam : task.getTaskParams()){

            taskValues.add(new TaskParamWithValueDto(
                task.getId()
                , taskParam.getParamName()
                , taskParam.getParamType()
                , (taskParam.getParamType() != TaskSampleParamType.FILE) ?
                    taskParam.getParamValue() :
                    Paths.get(taskParam.getParamValue()).getFileName().toString()
            ));
        }

        return new TaskProcessingLaunchedDto(
            taskSample.getName()
            , taskValues
        );
    }

    public List<TaskToProcParam> getTaskParamsFromLaunchingTaskDto(
        TaskToProc task
        , LaunchingTaskDto launchingTaskDto
    ) throws Exception{
        List<TaskToProcParam> taskParams = new ArrayList<>();
        
        for(TaskToProcValueDto toSaveParam : launchingTaskDto.values()){
            String paramValue = null;

            if(toSaveParam.value() instanceof MultipartFile){
                Path fileValueLocation = taskToProcService.saveFileTaskToProcParam(
                    task
                    , (MultipartFile) toSaveParam.value()
                );
                paramValue = fileValueLocation.toString();
            }else{
                paramValue = toSaveParam.value().toString();
            }

            taskParams.add(new TaskToProcParam(
                toSaveParam.paramName()
                , toSaveParam.paramType()
                , paramValue
            ));
        }

        return taskParams;
    }

    public TaskSamplesEditorDto createTaskSamplesEditorDto(List<TaskSample> taskSamples){
        List<TaskSampleInfoShowedInEditorDto> taskSamplesInfo = new ArrayList<>();
        for (var taskSample : taskSamples) {
            taskSamplesInfo.add(new TaskSampleInfoShowedInEditorDto(
                taskSample.getId()
                , taskSample.getName()
                , taskSample.getDescription()
            ));
        }
        return new TaskSamplesEditorDto(taskSamplesInfo);
    }

    public EditingTaskSampleDto createAddingTaskSampleDto(){
        return new EditingTaskSampleDto(
            null
            , null
            , null
            , null
            , null
            , null
            , null
            , null
            , null
            , null
        );
    }

    public EditingTaskSampleDto createEditingTaskSampleDto(
        TaskSample taskSample
    ){
        List<TaskSampleParam> params = taskSample.getParams();

        List<TaskSampleParamMainInfoDto> paramsMainfInfo = null;
        List<IntegerParamConstraintsDto> intConstraints = null;
        List<StringParamConstraintsDto> strConstraints = null;
        List<FileParamConstraintsDto> fileConstraints = null;

        if(params != null){
            paramsMainfInfo = new ArrayList<>();
            for(var param : params){
                String uuid = UUID.randomUUID().toString();

                paramsMainfInfo.add(new TaskSampleParamMainInfoDto(
                    param.getId()
                    , param.getName()
                    , uuid
                    , param.getType()
                ));
                switch (param.getType()) {
                    case INTEGER:
                        if(intConstraints == null){
                            intConstraints = new ArrayList<>();
                        }

                        var intParam = (TaskSampleIntegerParam) param;
                        intConstraints.add(new IntegerParamConstraintsDto(
                            uuid
                            , intParam.getMin()
                            , intParam.getMax()
                        ));
                        break;
                    case STRING:
                        if(strConstraints == null){
                            strConstraints = new ArrayList<>();
                        }

                        var strParam = (TaskSampleStringParam) param;
                        strConstraints.add(new StringParamConstraintsDto(
                            uuid
                        ));
                        break;
                    case FILE:
                        if(fileConstraints == null){
                            fileConstraints = new ArrayList<>();
                        }

                        var fileParam = (TaskSampleFileParam) param;
                        fileConstraints.add(new FileParamConstraintsDto(
                            uuid
                        ));
                        break;
                    default:
                        break;
                }
            }
        }

        return new EditingTaskSampleDto(
            taskSample.getId()
            , taskSample.getName()
            , taskSample.getDescription()
            , taskSample.getLaunchCommandTemplate()
            , null
            , Paths.get(taskSample.getScriptFilePath()).getFileName().toString()
            , paramsMainfInfo
            , intConstraints
            , strConstraints
            , fileConstraints
        );
    }

    public EditingTaskSampleDto addParamToTaskSample(
        EditingTaskSampleDto editingTaskSampleDto
        , TaskSampleParamType type
    ) throws Exception{

        List<IntegerParamConstraintsDto> intConstraints = editingTaskSampleDto.intConstraints();
        List<StringParamConstraintsDto> strConstraints = editingTaskSampleDto.strConstraints();
        List<FileParamConstraintsDto> fileConstraints = editingTaskSampleDto.fileConstraints();
        
        // TODO при добавлении/удаления параметра и страница обновляется исчезает информация о прикреплённом скрипте
        String paramName = "";
        
        //! TODO Убрать нахрен 
        //? TODO Это выглядит мега костыльно
        while(true){
            paramName = "No name " + (Integer.toString(1000 + (int)(ThreadLocalRandom.current().nextDouble() * ((9999 - 1000) + 1))));
            if(editingTaskSampleDto.paramsInfo() == null || !editingTaskSampleDto.paramsInfo().stream().map(TaskSampleParamMainInfoDto::name).collect(Collectors.toList()).contains(paramName)){
                break;
            }
        }

        String uuid = UUID.randomUUID().toString();
        TaskSampleParamMainInfoDto paramInfo = new TaskSampleParamMainInfoDto(
            null
            , paramName
            , uuid
            , type
        );

        switch (type) {
            case TaskSampleParamType.INTEGER:
                if (intConstraints == null) {
                    intConstraints = new ArrayList<>();
                }

                var intConstraint = new IntegerParamConstraintsDto(
                    uuid
                    , 0L
                    , 0L
                );
                intConstraints.add(intConstraint);
                break;
            case TaskSampleParamType.STRING:
                if(strConstraints == null){
                    strConstraints = new ArrayList<>();
                }

                var strConstraint = new StringParamConstraintsDto(
                    uuid
                );
                strConstraints.add(strConstraint);
                break;
            case TaskSampleParamType.FILE:
                if(fileConstraints == null){
                    fileConstraints = new ArrayList<>();
                }

                var fileConstraint = new FileParamConstraintsDto(
                    uuid
                );
                fileConstraints.add(fileConstraint);
                break;
            default:
                throw new Exception("Problems with creating task sample param");
        }

        var paramsInfo = editingTaskSampleDto.paramsInfo();
        if(paramsInfo == null){
            paramsInfo = new ArrayList<>();
        }
        paramsInfo.add(paramInfo);

        return new EditingTaskSampleDto(
            editingTaskSampleDto.id()
            , editingTaskSampleDto.name()
            , editingTaskSampleDto.description()
            , editingTaskSampleDto.launchCommandTemplate()
            , editingTaskSampleDto.scriptFile()
            , editingTaskSampleDto.scriptFileName()
            , paramsInfo
            , intConstraints
            , strConstraints
            , fileConstraints
        );
    }

    public EditingTaskSampleDto removeParamFromTaskSample(
        EditingTaskSampleDto editingTaskSampleDto
        , String uuidForRemove
    ) throws Exception{
        List<TaskSampleParamMainInfoDto> paramsInfo = editingTaskSampleDto.paramsInfo();

        List<IntegerParamConstraintsDto> intConstraints = editingTaskSampleDto.intConstraints();
        List<StringParamConstraintsDto> strConstraints = editingTaskSampleDto.strConstraints();
        List<FileParamConstraintsDto> fileConstraints = editingTaskSampleDto.fileConstraints();

        for(int i = 0; i < paramsInfo.size(); i++){
            if(paramsInfo.get(i).uuid().equals(uuidForRemove)){
                var removedParam = paramsInfo.remove(i);

                switch (removedParam.type()) {
                    case INTEGER:
                        for(int u = 0, n = intConstraints.size(); u < n; u++){
                            if(intConstraints.get(u).uuid().equals(removedParam.uuid())){
                                intConstraints.remove(u);
                                break;
                            }
                        }
                        break;
                    case STRING:
                        for(int u = 0, n = strConstraints.size(); u < n; u++){
                            if(strConstraints.get(u).uuid().equals(removedParam.uuid())){
                                strConstraints.remove(u);
                                break;
                            }
                        }
                        break;
                    case FILE:
                        for(int u = 0, n = fileConstraints.size(); u < n; u++){
                            if(fileConstraints.get(u).uuid().equals(removedParam.uuid())){
                                fileConstraints.remove(u);
                                break;
                            }
                        }
                        break;
                    default:
                        throw new Exception("Problems with removing param from task sample");
                }
                break;
            }
        }

        return new EditingTaskSampleDto(
            editingTaskSampleDto.id()
            , editingTaskSampleDto.name()
            , editingTaskSampleDto.description()
            , editingTaskSampleDto.launchCommandTemplate()
            , editingTaskSampleDto.scriptFile()
            , editingTaskSampleDto.scriptFileName()
            , paramsInfo
            , intConstraints
            , strConstraints
            , fileConstraints
        );
    }

    public TaskSample getTaskSampleFromEditingTaskSampleDto(
        EditingTaskSampleDto editingTaskSampleDto
    ) throws Exception{
        TaskSample currentTaskSampleInDB = taskSampleService.getTaskSample(editingTaskSampleDto.id());

        List<TaskSampleParamMainInfoDto> paramsInfo = editingTaskSampleDto.paramsInfo();

        List<IntegerParamConstraintsDto> intConstraints = editingTaskSampleDto.intConstraints();
        List<StringParamConstraintsDto> strConstraints = editingTaskSampleDto.strConstraints();
        List<FileParamConstraintsDto> fileConstraints = editingTaskSampleDto.fileConstraints();

        List<TaskSampleParam> taskSampleParams = null;

        if(paramsInfo != null){
            taskSampleParams = new ArrayList<>();
            for(TaskSampleParamMainInfoDto paramInfo : paramsInfo){

                switch (paramInfo.type()) {
                    case INTEGER:
                        IntegerParamConstraintsDto intConstraint = null;
                        for (var oneOfConstraints : intConstraints) {
                            if(oneOfConstraints.uuid().equals(paramInfo.uuid())){
                                intConstraint = oneOfConstraints;
                                break;
                            }
                        }
                        if (intConstraint == null) {
                            throw new NullPointerException("Not any constraint matches");
                        }
                        taskSampleParams.add(new TaskSampleIntegerParam(
                            paramInfo.id()
                            , paramInfo.name()
                            , intConstraint.min()
                            , intConstraint.max()
                        ));
                        break;
                    case STRING:
                        StringParamConstraintsDto strConstraint = null;
                        for (var oneOfConstraints : strConstraints) {
                            if(oneOfConstraints.uuid().equals(paramInfo.uuid())){
                                strConstraint = oneOfConstraints;
                                break;
                            }
                        }
                        if (strConstraint == null) {
                            throw new NullPointerException("Not any constraint matches");
                        }
                        taskSampleParams.add(new TaskSampleStringParam(
                            paramInfo.id()
                            , paramInfo.name()
                        ));
                        break;
                    case FILE:
                        FileParamConstraintsDto fileConstraint = null;
                        for (var oneOfConstraints : fileConstraints) {
                            if(oneOfConstraints.uuid().equals(paramInfo.uuid())){
                                fileConstraint = oneOfConstraints;
                                break;
                            }
                        }
                        if (fileConstraint == null) {
                            throw new NullPointerException("Not any constraint matches");
                        }
                        taskSampleParams.add(new TaskSampleFileParam(
                            paramInfo.id()
                            , paramInfo.name()
                        ));
                        break;
                    default:
                        throw new Exception("Problems with getting task sample from editing task sample dto");
                }
            }
        }

        return new TaskSample(
            editingTaskSampleDto.id()
            , editingTaskSampleDto.name()
            , editingTaskSampleDto.description()
            , editingTaskSampleDto.launchCommandTemplate()
            , editingTaskSampleDto.scriptFile()
            , currentTaskSampleInDB.getScriptFilePath()
            , taskSampleParams
        );
    }
}
