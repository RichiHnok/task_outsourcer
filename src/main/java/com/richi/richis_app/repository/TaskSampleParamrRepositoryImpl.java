package com.richi.richis_app.repository;

import org.springframework.stereotype.Repository;

import com.richi.richis_app.entity.TaskSampleParam;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class TaskSampleParamrRepositoryImpl implements TaskSampleParamRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TaskSampleParam getTaskSampleParam(int id) {
        return entityManager.find(TaskSampleParam.class, id);
    }

    @Override
    public void removeParamFromTaskSample(int id) {
        Query query = entityManager.createQuery("delete from TaskSampleParam where id =:taskSampleParamId");
        query.setParameter("taskSampleParamId", id);
        query.executeUpdate();
    }
    
}
