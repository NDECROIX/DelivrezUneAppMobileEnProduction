package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private TaskDAO taskDAO;

    public TaskDataRepository(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public LiveData<List<Task>> getTasksByProject(long projectId){
        return this.taskDAO.getTasksByProject(projectId);
    }

    public LiveData<List<Task>> getTasks(){
        return this.taskDAO.getTasks();
    }

    public void insertTask(Task task){
        this.taskDAO.insertTask(task);
    }

    public void updateTask(Task task){
        this.taskDAO.updateTask(task);
    }

    public void deleteTask(Task task){
        taskDAO.deleteTask(task);
    }

}
