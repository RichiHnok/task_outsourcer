package com.richi.web_part.dto.personalCabinet;

import java.util.List;

import org.springframework.data.domain.Page;

public record PersonalCabinetDto(
    String userFirstname
    , String userSurname
    , Page<TaskInfoForPersonalCabinetDto> pageOfUserTasks
    , List<Integer> pageNumbers
) {
    
}
