package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.Utils.LiveDataTestUtil;
import com.cleanup.todoc.database.TodocMasterDatabase;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
public class TaskDAOTest {

    // FOR DATA
    private TodocMasterDatabase database;

    private static Task TASK = new Task(1L, "Name", Calendar.getInstance().getTimeInMillis());

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
     * Insert and retrieve a task in the database
     */
    @Test
    public void myTaskDAOTest_insertAndGetTask_withSuccess() throws Exception {
        this.database.taskDAO().insertTask(TASK);
        Task task = LiveDataTestUtil.getValue(this.database.taskDAO().getTasks()).get(0);
        assertTrue(task.getName().equals(TASK.getName()) && task.getCreationTimestamp() == TASK.getCreationTimestamp());
    }

    /**
     * Insert and delete a task in the database
     */
    @Test
    public void myTaskDAOTest_insertAndDeleteTask_withSuccess() throws Exception {
        this.database.taskDAO().insertTask(TASK);
        Task task = LiveDataTestUtil.getValue(this.database.taskDAO().getTasks()).get(0);
        database.taskDAO().deleteTask(task);
        assertTrue(LiveDataTestUtil.getValue(this.database.taskDAO().getTasks()).isEmpty());
    }
}
