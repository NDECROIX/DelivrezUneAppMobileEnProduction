package com.cleanup.todocmaster;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todocmaster.utils.LiveDataTestUtil;
import com.cleanup.todocmaster.database.TodocMasterDatabase;
import com.cleanup.todocmaster.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;


@RunWith(AndroidJUnit4.class)
public class ProjectDAOTest {

    // FOR DATA
    private TodocMasterDatabase database;


    // Perform the test on the main thread
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), TodocMasterDatabase.class)
                .allowMainThreadQueries()
                .addCallback(TodocMasterDatabase.preAddProjectsToDatabase())
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    /**
     * Check if the database has the right projects
     */
    @Test
    public void getProjects_withSuccess() throws Exception {
        List<Project> projectsDatabase = LiveDataTestUtil.getValue(this.database.projectDAO().getProjects());
        assertArrayEquals(projectsDatabase.toArray(), Project.getAllProjects());
    }
}
