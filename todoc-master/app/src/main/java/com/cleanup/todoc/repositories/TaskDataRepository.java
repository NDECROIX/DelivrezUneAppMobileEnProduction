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

    // --- GET TASKS ---
    public LiveData<List<Task>> getTasks() {
        return this.taskDAO.getTasks();
    }

    // --- INSERT TASK ---
    public void insertTask(Task task) {
        this.taskDAO.insertTask(task);
    }

    // --- DELETE TASK ---
    public void deleteTask(Task task) {
        taskDAO.deleteTask(task);
    }
    public void deleteTasks() {
        taskDAO.deleteTasks();
    }

}