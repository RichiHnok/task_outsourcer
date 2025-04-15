package com.richi.web_part.mapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.User;
import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.entity.taskToProc.TaskToProc;
import com.richi.common.entity.taskToProc.TaskToProcParam;
import com.richi.common.enums.TaskSampleParamType;
import com.richi.common.enums.TaskToProcStatus;
import com.richi.common.service.TaskToProcService;
import com.richi.web_part.dto.commonDto.TaskParamInfoDto;
import com.richi.web_part.dto.controlPanel.ControlPanelDto;
import com.richi.web_part.dto.controlPanel.TaskInfoForControlPanelDto;
import com.richi.web_part.dto.personalCabinet.PersonalCabinetDto;
import com.richi.web_part.dto.personalCabinet.TaskInfoForPersonalCabinetDto;
import com.richi.web_part.dto.taskProcessingLaunched.TaskParamWithValueDto;
import com.richi.web_part.dto.taskProcessingLaunched.TaskProcessingLaunchedDto;
import com.richi.web_part.dto.taskToProcVal.LaunchingTaskDto;
import com.richi.web_part.dto.taskToProcVal.TaskToProcValueDto;

// TODO Пока что это будет общий маппер для всех ДТО-х. Когда разрастётся, разбить на более мелкие мапперы
@Service
public class TemporaryMapper {

    private final TaskToProcService taskToProcService;
    
    public TemporaryMapper(
        TaskToProcService taskToProcService
    ) {
        this.taskToProcService = taskToProcService;
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
}
