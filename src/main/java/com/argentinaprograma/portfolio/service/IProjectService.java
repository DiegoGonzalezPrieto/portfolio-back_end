package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.Project;
import java.util.List;

public interface IProjectService {

    // get all project
    public List<Project> getProjects();

    // create project
    public Project saveProject(Project project);

    // delete project
    public boolean deleteProject(Long id);

    // find project
    public Project findProject(Long id);

    // find all Project by personId
    public List<Project> getProjectsByPersonID(Long personId);

}
