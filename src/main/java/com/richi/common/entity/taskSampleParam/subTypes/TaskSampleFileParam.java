package com.richi.common.entity.taskSampleParam.subTypes;

import org.springframework.web.multipart.MultipartFile;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "task_sample_param_file")
public class TaskSampleFileParam extends TaskSampleParam{
    
    @Column(name = "file_path")
    private String paramFilePath;

    @Transient
    private MultipartFile paramFile;

    public TaskSampleFileParam() {
    }

    public String getParamFilePath() {
        return paramFilePath;
    }

    public void setParamFilePath(String paramFilePath) {
        this.paramFilePath = paramFilePath;
    }

    public MultipartFile getParamFile() {
        return paramFile;
    }

    public void setParamFile(MultipartFile paramFile) {
        this.paramFile = paramFile;
    }
}
