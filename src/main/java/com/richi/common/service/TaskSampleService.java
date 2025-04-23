package com.richi.common.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richi.common.entity.TaskSample;
import com.richi.common.repository.TaskSampleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskSampleService {
    
    private final TaskSampleRepository repository;
    private final StorageService storageService;
    private final FileFolderManipulationService fileFolderManipulationService;

    public TaskSampleService(
        TaskSampleRepository repository
        , StorageService storageService
        , @Lazy FileFolderManipulationService fileFolderManipulationService
    ){
        this.repository = repository;
        this.storageService = storageService;
        this.fileFolderManipulationService = fileFolderManipulationService;
    }

    @Transactional
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

    @Transactional
    public TaskSample saveTaskSample(
        TaskSample taskSample
    ) throws IOException {

        // TODO При заугрузке скрипта, переименовывать его используя название шаблона
        // TODO при изменении параметров удалять старый шаблон и создавать новый, а не изменять старый, чтобы в базе данных в таблице task_to_proc_не былопутаницы с параметрами

        try {
            TaskSample currentTaskSampleInDB = getTaskSample(taskSample.getId());
            String olderScriptPath = currentTaskSampleInDB.getScriptFilePath();
            if(!taskSample.getScriptFile().isEmpty() && olderScriptPath != null){
                Path olderFilePath = Path.of(olderScriptPath);
                storageService.deleteFile(olderFilePath);
            }

            if(!taskSample.getScriptFile().isEmpty()){
                // TODO стльно торопился поэтому пришлось накодить сохранение файла и запись пути сохранения в базу. Надо поправить
                Path relativePathToStore = storageService.storeInFolder(
                    taskSample.getScriptFile()
                    , fileFolderManipulationService.getFolderForStoringTaskSampleScriptFile(taskSample)
                );
                taskSample.setScriptFilePath(relativePathToStore.toString());
            }else{
                taskSample.setScriptFilePath(olderScriptPath);
            }
        } catch (EntityNotFoundException e) {
            taskSample = repository.save(taskSample);
            if(!taskSample.getScriptFile().isEmpty()){
                Path relativePathToStore = storageService.storeInFolder(
                    taskSample.getScriptFile()
                    , fileFolderManipulationService.getFolderForStoringTaskSampleScriptFile(taskSample)
                );
                taskSample.setScriptFilePath(relativePathToStore.toString());
            }
            
        }

        return repository.save(taskSample);
    }

    public boolean checkIfTaskSampleExists(Integer taskSampleId){
        return repository.existsById(taskSampleId);
    }
}
