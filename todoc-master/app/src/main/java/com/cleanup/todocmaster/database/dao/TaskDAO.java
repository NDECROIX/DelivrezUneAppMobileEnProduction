package com.cleanup.todocmaster.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cleanup.todocmaster.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM Task")
    void deleteTasks();
}