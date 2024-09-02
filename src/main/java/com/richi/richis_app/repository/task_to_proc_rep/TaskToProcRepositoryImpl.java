package com.richi.richis_app.repository.task_to_proc_rep;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.richi.richis_app.entity.TaskToProc;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class TaskToProcRepositoryImpl implements TaskToProcRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteTaskToProc(int id) {
        Query query = entityManager.createQuery("delete from TaskToProc where id =:taskToProcId");
        query.setParameter("taskToProcId", id);
        query.executeUpdate();
    }

    @Override
    public List<TaskToProc> getAllTasksToProc() {
        Query query = entityManager.createQuery("from TaskToProc");
        List<TaskToProc> allTasksToProc = castList(TaskToProc.class, query.getResultList());

        return allTasksToProc;
    }

    @Override
    public TaskToProc getTaskToProc(int id) {
        return entityManager.find(TaskToProc.class, id);
    }

    @Override
    public void saveTaskToProc(TaskToProc taskToProc) {
        entityManager.merge(taskToProc);
    }
    
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
        // Этот метод нужен чтобы не было какой-то там нежелательной ситуации
        List<T> r = new ArrayList<T>(c.size());
        for(Object o: c)
        r.add(clazz.cast(o));
        return r;
    }
}
