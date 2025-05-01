package com.richi.web_part.dto.editingTaskSample;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.richi.web_part.dto.editingTaskSample.taskSampleParam.TaskSampleParamMainInfoDto;
import com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.FileParamConstraintsDto;
import com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.IntegerParamConstraintsDto;
import com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.StringParamConstraintsDto;

import jakarta.validation.Valid;

public record EditingTaskSampleDto(
    Integer id,
    String name,
    String description,
    String launchCommandTemplate,
    MultipartFile scriptFile,
    String scriptFileName,
    List<TaskSampleParamMainInfoDto> paramsInfo,

    @Valid List<IntegerParamConstraintsDto> intConstraints,
    @Valid List<StringParamConstraintsDto> strConstraints,
    List<FileParamConstraintsDto> fileConstraints
) {
    
}
