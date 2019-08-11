package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

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
    public static TodocMasterDatabase getINSTANCE(Context context){
        if (INSTANCE == null){
            synchronized (TodocMasterDatabase.class){
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

    private static Callback preAddProjectsToDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                db.insert("Project", OnConflictStrategy.IGNORE,
                        createProject(1L, "Projet Tartampion", 0xFFEADAD1));
                db.insert("Project", OnConflictStrategy.IGNORE,
                        createProject(2L, "Projet Lucidia", 0xFFB4CDBA));
                db.insert("Project", OnConflictStrategy.IGNORE,
                        createProject(3L, "Projet Circus", 0xFFA3CED2));
            }
        };
    }

    private static ContentValues createProject(long id, String name, int color){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("color", color);
        return contentValues;
    }
}
