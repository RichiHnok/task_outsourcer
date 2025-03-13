package com.richi.common.service;

import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Service;

import com.richi.common.entity.TaskSample;
import com.richi.common.repository.TaskSampleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskSampleService {
    
    private final TaskSampleRepository repository;

    public TaskSampleService(
        TaskSampleRepository repository
    ){
        this.repository = repository;
    }

    public void deleteTaskSampleById(int taskSampleId) {
        repository.findById(taskSampleId).orElseThrow(
            () -> new EntityNotFoundException("There is no task sample with ID:: " + taskSampleId)
        );
        repository.deleteById(taskSampleId);
    }

    public List<TaskSample> getAllTaskSamples() {
        return repository.findAll();
    }

    public TaskSample getTaskSample(Integer taskSampleId) {
        if (taskSampleId == null) {
            throw new EntityNotFoundException("Cannot return Task Sample because of null input");
        }
        return repository.findById(taskSampleId).orElseThrow(
            () -> new EntityNotFoundException("There is no task sample with ID:: " + taskSampleId)
        );
    }

    public TaskSample saveTaskSample(TaskSample taskSample) {
        return repository.save(taskSample);
    }

    public boolean checkIfTaskSampleExists(int taskSampleId){
        return repository.existsById(taskSampleId);
    }

    @Deprecated
    public Path getFolderForStoringScriptFile(TaskSample taskSample) {
        if(!checkIfTaskSampleExists(taskSample.getId())){
            throw new EntityNotFoundException("There is no task sample with ID:: " + taskSample.getId());
        }
        StringBuilder relativePathBuilder = new StringBuilder();
        relativePathBuilder.append("src\\main\\resources\\files\\samples\\").append("sample"+taskSample.getId());
        return Path.of(relativePathBuilder.toString());
    }
}
