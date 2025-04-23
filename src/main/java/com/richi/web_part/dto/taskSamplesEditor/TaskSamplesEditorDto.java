package com.richi.web_part.dto.taskSamplesEditor;

import java.util.List;

public record TaskSamplesEditorDto(
    List<TaskSampleInfoShowedInEditorDto> taskSamplesInfo
) {
}
