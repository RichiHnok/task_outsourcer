package com.richi.richis_app.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.richi.richis_app.entity.TaskSample;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class TaskSampleRepositoryImpl implements TaskSampleRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TaskSample> getAllTaskSamples() {
        
        Query query = entityManager.createQuery("from TaskSample");
        List<TaskSample> allTaskSamples = castList(TaskSample.class, query.getResultList());

        return allTaskSamples;
    }
    
    @Override
    public void saveTaskSample(TaskSample taskSample) {
        entityManager.merge(taskSample);
    }

    @Override
    public void deleteTaskSample(int id) {
        Query query = entityManager.createQuery("delete from TaskSample where id =:taskSampleId");
        query.setParameter("taskSampleId", id);
        query.executeUpdate();
    }

    @Override
    public TaskSample getTaskSample(int id) {
        return entityManager.find(TaskSample.class, id);
    }

    // @Override
    // public void addParamToTaskSample(TaskSample taskSample, TaskSampleParam param) {
    //     taskSample.addParamToTaskSample(param);
        
    // }

    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for(Object o: c)
        r.add(clazz.cast(o));
        return r;
    }
}
