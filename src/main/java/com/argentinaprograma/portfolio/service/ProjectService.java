package com.argentinaprograma.portfolio.service;



import com.argentinaprograma.portfolio.model.Project;
import com.argentinaprograma.portfolio.repository.IProjectRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private IProjectRepo projectRepo;

    @Override
    public List<Project> getProjects() {
        List<Project> ps = projectRepo.findAll();
        return ps;
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepo.save(project);
    }

    @Override
    public boolean deleteProject(Long id) {
        try {
            projectRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Project findProject(Long id) {
        Project p = projectRepo.findById(id).orElse(null);
        return p;
    }

    @Override
    public List<Project> getProjectsByPersonID(Long personId) {
        return projectRepo.findAllByPersonId(personId);
    }

}
