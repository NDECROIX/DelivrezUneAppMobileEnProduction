package com.cleanup.todocmaster.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todocmaster.database.dao.ProjectDAO;
import com.cleanup.todocmaster.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private ProjectDAO projectDAO;

    public ProjectDataRepository(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    // --- GET PROJECTS ---
    public LiveData<List<Project>> getProjects() {
        return this.projectDAO.getProjects();
    }

}