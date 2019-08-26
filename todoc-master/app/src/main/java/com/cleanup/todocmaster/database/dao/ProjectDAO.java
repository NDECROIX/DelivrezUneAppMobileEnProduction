package com.cleanup.todocmaster.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.cleanup.todocmaster.model.Project;

import java.util.List;

@Dao
public interface ProjectDAO {

    @Query("SELECT * From Project")
    LiveData<List<Project>> getProjects();
}