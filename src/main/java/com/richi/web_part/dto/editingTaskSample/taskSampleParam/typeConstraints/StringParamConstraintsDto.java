package com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints;

import com.richi.web_part.validation.editingTaskSample.checkingStringParam.CheckStringParamConstraints;

/**
 * DTO для тарнспортировки ограничений для строкового
 * в представление и из него
 * <p>
 * @param uuid :{@code String} - нужен для сопоставления с основной информацией параметра в шаблонизаторе
 * @param regExConstraint :{@code String} - регулярное выражение
 * @param hintValue :{@code String} - подсказака-прмиер вводимого значения
 */

@CheckStringParamConstraints
public record StringParamConstraintsDto(
    String uuid
    , String regExConstraint
    , String hintValue
) {

}
