package com.cleanup.todoc;

import android.content.Context;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cleanup.todoc.database.TodocMasterDatabase;
import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onIdle;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.utils.TestUtils.withRecyclerView;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity activity;

    private static Task TASK1 = new Task(3L, "Tâche Circus", Calendar.getInstance().getTimeInMillis());
    private static Task TASK2 = new Task(1L, "Tâche Tartampion", Calendar.getInstance().getTimeInMillis() + 1000);
    private static Task TASK3 = new Task(2L, "Tâche Lucidia", Calendar.getInstance().getTimeInMillis() + 2000);

    private TaskDAO taskDao;

    @Before
    public void setUp() {
        activity = rule.getActivity();
        assertThat(activity, notNullValue());
        Context context = activity.getApplicationContext();
        taskDao = TodocMasterDatabase.getINSTANCE(context).taskDAO();
        taskDao.deleteTasks();
    }

    /**
     * Add 3 tasks in database by DAO
     */
    @DataPoint
    public void addTasksInRecyclerView() {
        taskDao.insertTask(TASK1);
        taskDao.insertTask(TASK2);
        taskDao.insertTask(TASK3);
    }

    /**
     * Three tasks are successfully displayed in recycler view
     */
    @Test
    public void checkTasksInRecyclerView(){
        addTasksInRecyclerView();
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);
        onIdle();
        assertThat(listTasks.getAdapter().getItemCount(), equalTo(3));
    }

    /**
     * Click on the floating action button to display the add Task dialog
     */
    @Test
    public void clickOnFab_ShowDialog(){
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withText(R.string.add_task)).check(matches(isDisplayed()));
    }

    /**
     * Add Task in the recycler view
     */
    @Test
    public void addTask() {
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche Circus"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText(containsString("Circus"))).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        // Check that lblTask is not displayed anymore
        assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
        // Check that recyclerView is displayed
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
        // Check that it contains one element only
        assertThat(listTasks.getAdapter().getItemCount(), equalTo(1));
    }

    /**
     * Remove a task from the recycler view
     */
    @Test
    public void removeTask() {
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche Lucidia"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText(containsString("Lucidia"))).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.img_delete)).perform(click());

        // Check that lblTask is displayed
        assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
        // Check that recyclerView is not displayed anymore
        assertThat(listTasks.getVisibility(), equalTo(View.GONE));
    }

    /**
     * Sort tasks by project name A Z
     */
    @Test
    public void sortTasksByProjectNameAZ() {
        addTasksInRecyclerView();

        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Tâche Circus")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("Tâche Lucidia")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("Tâche Tartampion")));
    }

    /**
     * Sort tasks by project name Z A
     */
    @Test
    public void sortTasksByProjectNameZA() {
        addTasksInRecyclerView();

        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Tâche Tartampion")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("Tâche Lucidia")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("Tâche Circus")));
    }

    /**
     * Sort tasks by most recent
     */
    @Test
    public void sortTasksByOldFirst() {
        addTasksInRecyclerView();

        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Tâche Circus")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("Tâche Tartampion")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("Tâche Lucidia")));
    }

    /**
     * Sort tasks by most oldest
     */
    @Test
    public void sortTasksByRecentFirst() {
        addTasksInRecyclerView();

        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_recent_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Tâche Lucidia")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("Tâche Tartampion")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("Tâche Circus")));
    }
}
