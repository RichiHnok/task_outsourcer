package com.richi.web_part.dto.launchingTask;

import java.util.List;

import com.richi.web_part.dto.launchingTask.paramInfo.ParamValueInfo;
import com.richi.web_part.dto.launchingTask.paramInfo.additionalParamInfo.AdditionalFileParamInfo;
import com.richi.web_part.dto.launchingTask.paramInfo.additionalParamInfo.AdditionalIntegerParamInfo;
import com.richi.web_part.dto.launchingTask.paramInfo.additionalParamInfo.AdditionalStringParamInfo;

import jakarta.validation.Valid;

/**
 * DTO для передачи информации о запускаемой задаче.
 * <p>
 * @param taskSampleId :{@code Integer}
 * @param taskSampleName :{@code String}
 * @param taskSampleDescription :{@code String}
 * @param values :{@code List<ParamValueInfo>} - основная информация о параметрах для
 * запуска задачи.
 * @param intAdditionalInfo :{@code List<AdditionalIntegerParamInfo>}
 * - дополнительная информация о параметрах типа INTEGER
 * @param strAdditionalInfo :{@code List<AdditionalStringParamInfo>}
 * - дополнительная информация о параметрах типа STRING
 * @param fileAdditionalInfo :{@code List<AdditionalFileParamInfo>}
 * - дополнительная информация о параметрах типа FILE
 */

public record LaunchingTaskDto(
    Integer taskSampleId
    , String taskSampleName
    , String taskSampleDescription
    , @Valid List<ParamValueInfo> values

    , List<AdditionalIntegerParamInfo> intAdditionalInfo
    , List<AdditionalStringParamInfo> strAdditionalInfo
    , List<AdditionalFileParamInfo> fileAdditionalInfo
){

}
