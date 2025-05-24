package com.richi.web_part.dto.launchingTask.paramInfo;

import com.richi.common.enums.TaskSampleParamType;
import com.richi.web_part.validation.launchingTask.CheckParamInput;

/**
 * DTO для передачи от пользователя основной информации о параметре,
 * необходимой для запуска выполнения задачи.<p>
 * @param uuid :{@code String} нужен для сопоставления с
 * дополнительной инормацией о параметре.
 * @param taskSampleParamId :{@code Integer} - идентификатор параметра
 * в шаблоне. Нужен для получения дополнительной информации при проверке
 * на корректность значения
 * @param name :{@code String} - имя параметра
 * @param type :{@code TaskSampleParamType} - тип параметра
 * @param description :{@code String} - описание параметра
 * @param value :{@code Object} - значение параметра, с которым
 * будет запускаться выполнение задачи
 */

 @CheckParamInput
public record ParamValueInfo(
    String uuid
    , Integer taskSampleParamId
    , String name
    , TaskSampleParamType type
    , String description
    , Object value
) {
    
}
