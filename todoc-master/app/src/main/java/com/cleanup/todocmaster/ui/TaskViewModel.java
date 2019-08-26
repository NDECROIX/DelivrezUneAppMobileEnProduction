package com.cleanup.todocmaster.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todocmaster.model.Project;
import com.cleanup.todocmaster.model.Task;
import com.cleanup.todocmaster.repositories.ProjectDataRepository;
import com.cleanup.todocmaster.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // --- REPOSITORIES ---
    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    // --- DATA ---
    public TaskViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    // --------------
    // FOR PROJECT
    // --------------

    LiveData<List<Project>> getProjects() {
        return this.projectDataSource.getProjects();
    }

    // --------------
    // FOR TASK
    // --------------

    LiveData<List<Task>> getTasks() {
        return taskDataSource.getTasks();
    }

    void insertTask(Task task) {
        executor.execute(() -> taskDataSource.insertTask(task));
    }

    void deleteTask(Task task) {
        executor.execute(() -> taskDataSource.deleteTask(task));
    }
}
