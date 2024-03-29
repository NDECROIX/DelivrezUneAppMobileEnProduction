package com.cleanup.todocmaster.injections;

import android.content.Context;

import com.cleanup.todocmaster.database.TodocMasterDatabase;
import com.cleanup.todocmaster.repositories.ProjectDataRepository;
import com.cleanup.todocmaster.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    private static TaskDataRepository provideTaskDataSource(Context context) {
        TodocMasterDatabase database = TodocMasterDatabase.getINSTANCE(context);
        return new TaskDataRepository(database.taskDAO());
    }

    private static ProjectDataRepository provideProjectDataSource(Context context) {
        TodocMasterDatabase database = TodocMasterDatabase.getINSTANCE(context);
        return new ProjectDataRepository(database.projectDAO());
    }

    private static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceProject, dataSourceTask, executor);
    }
}