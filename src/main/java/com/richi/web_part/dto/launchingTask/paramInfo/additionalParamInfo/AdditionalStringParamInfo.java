package com.richi.web_part.dto.launchingTask.paramInfo.additionalParamInfo;

/**
 * DTO для передачи дополнительной информации о параметре
 * типа STRING.
 * <p>
 * @param uuid :{@code String} нужен для сопоставления с
 * основной инормацией о параметре
 * @param description :{@code String} описание параметра,
 * что в него надо и можно записывать
 * @param hintValue :{@code String} подсказка того, как
 * должно выглядеть вводимое значение
 */

public record AdditionalStringParamInfo(
    String uuid
    , String description
    , String hintValue
) {
    
}
