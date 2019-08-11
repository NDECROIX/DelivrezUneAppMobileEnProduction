package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private ProjectDAO projectDAO;

    public ProjectDataRepository(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    // --- GET PROJECT ---
    public LiveData<List<Project>> getProjects(){
        return this.projectDAO.getProjects();
    }

    public LiveData<Project> getProject(long id){
        return this.projectDAO.getProjectById(id);
    }

}