package com.richi.web_part.dto.controlPanel;

import java.util.List;

import org.springframework.data.domain.Page;

public record ControlPanelDto(
    Page<TaskInfoForControlPanelDto> pageOfUsersTasks
    , List<Integer> pageNumbers
) {
    
}
