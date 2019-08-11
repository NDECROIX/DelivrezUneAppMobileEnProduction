package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

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

    public LiveData<List<Project>> getProjects(){
        return this.projectDataSource.getProjects();
    }

    public LiveData<Project> getProject(long projectId){
        return this.projectDataSource.getProject(projectId);
    }

    // --------------
    // FOR TASK
    // --------------

    public LiveData<List<Task>> getTasksByProject(long projectId){
        return taskDataSource.getTasksByProject(projectId);
    }

    public LiveData<List<Task>> getTasks(){
        return taskDataSource.getTasks();
    }

    public void insertTask(Task task){
        executor.execute(() -> taskDataSource.insertTask(task));
    }

    public void updateTask(Task task){
        executor.execute(() -> taskDataSource.updateTask(task));
    }

    public void deleteTask(Task task){
        executor.execute(() -> taskDataSource.deleteTask(task));
    }
}
