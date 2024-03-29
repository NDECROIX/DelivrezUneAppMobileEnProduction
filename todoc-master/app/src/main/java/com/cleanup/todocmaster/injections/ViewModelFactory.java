package com.cleanup.todocmaster.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cleanup.todocmaster.repositories.ProjectDataRepository;
import com.cleanup.todocmaster.repositories.TaskDataRepository;
import com.cleanup.todocmaster.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    // --- REPOSITORIES ---
    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    ViewModelFactory(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(projectDataSource, taskDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}