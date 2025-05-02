package com.richi.web_part.dto.launchingTask.paramInfo.additionalParamInfo;

/**
 * DTO для передачи дополнительной информации о параметре
 * типа INTEGER.
 * <p>
 * @param uuid :{@code String} нужен для сопоставления с
 * основной инормацией о параметре
 * @param description :{@code String} описание параметра,
 * что в него надо и можно записывать
 * @param minConstraint :{@code String} минимальное значение, которое
 * может принимать параметр.
 * @param maxConstraint :{@code String} максимальное значение, которое
 * может принимать параметр.
 */

public record AdditionalIntegerParamInfo(
    String uuid
    , String description
    , String minConstraint
    , String maxConstraint
) {
    
}
