package com.richi.web_part.dto.launchingTask.paramInfo.additionalParamInfo;

/**
 * DTO для передачи дополнительной информации о параметре
 * типа FILE.
 * <p>
 * @param uuid :{@code String} нужен для сопоставления с
 * основной инормацией о параметре.
 * @param description :{@code String} описание параметра,
 * что в него надо и можно записывать
 */

public record AdditionalFileParamInfo(
    String uuid
    , String description
) {
    
}
