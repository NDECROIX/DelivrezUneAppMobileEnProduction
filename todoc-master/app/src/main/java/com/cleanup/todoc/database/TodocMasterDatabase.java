package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TodocMasterDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile TodocMasterDatabase INSTANCE;

    // --- DAO ---
    public abstract ProjectDAO projectDAO();

    public abstract TaskDAO taskDAO();

    // --- INSTANCE ---
    public static TodocMasterDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocMasterDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocMasterDatabase.class, "MyDatabase.db")
                            .addCallback(preAddProjectsToDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Insert projects in the database
    @VisibleForTesting
    public static Callback preAddProjectsToDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                for (Project project : Project.getAllProjects()) {
                    db.insert("Project", OnConflictStrategy.IGNORE,
                            createProject(project));
                }
            }
        };
    }

    private static ContentValues createProject(Project project) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", project.getId());
        contentValues.put("name", project.getName());
        contentValues.put("color", project.getColor());
        return contentValues;
    }
}