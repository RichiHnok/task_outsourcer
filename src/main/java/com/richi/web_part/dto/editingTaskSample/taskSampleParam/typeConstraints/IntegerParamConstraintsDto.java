package com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints;

import com.richi.web_part.validation.editingTaskSample.checkIntegerParam.CheckIntegerParamConstraints;

/**
 * DTO для тарнспортировки ограничений для целочисленного параметра
 * в представление и из него
 * <p>
 * @param String uuid - нужен для сопоставления с основной информацией параметра в шаблонизаторе
 * @param String min - минимальное допустимое значение
 * @param String max - максимальное допустимое значение
 */

@CheckIntegerParamConstraints
public record IntegerParamConstraintsDto(
    String uuid,
    String min,
    String max
){

}