package com.richi.web_part.mapper;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.richi.common.entity.User;
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
}
