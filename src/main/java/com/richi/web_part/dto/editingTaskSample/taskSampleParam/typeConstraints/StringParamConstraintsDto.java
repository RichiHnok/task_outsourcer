package com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints;

import com.richi.web_part.validation.editingTaskSample.checkingStringParam.CheckStringParamConstraints;

/**
 * DTO для тарнспортировки ограничений для строкового
 * в представление и из него
 * <p>
 * @param String {@code uuid} - нужен для сопоставления с основной информацией параметра в шаблонизаторе
 * @param String {@code regExConstraint} - регулярное выражение
 * @param String {@code hintValue} - подсказака-прмиер вводимого значения
 */
@CheckStringParamConstraints
public record StringParamConstraintsDto(
    String uuid
    , String regExConstraint
    , String hintValue
) {

}
