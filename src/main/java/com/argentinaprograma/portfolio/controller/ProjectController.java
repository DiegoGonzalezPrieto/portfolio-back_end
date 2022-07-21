package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.Person;
import com.argentinaprograma.portfolio.model.Project;
import com.argentinaprograma.portfolio.security.model.UserSecurity;
import com.argentinaprograma.portfolio.service.IProjectService;
import com.argentinaprograma.portfolio.service.IPersonService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    IPersonService personService;

    @Autowired
    private IProjectService projectService;
    
    @GetMapping ("/get/all")
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projects = projectService.getProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    
    @GetMapping ("/get")
    public ResponseEntity<Project> getProject(@RequestParam(value = "id") Long id) {
        Project project = projectService.findProject(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    
    @GetMapping ("/person")
    public ResponseEntity<List<Project>> getProjectsByPersonId(@RequestParam(value = "id") Long personId) {
        List<Project> projectList = new ArrayList();
        if (personService.findPerson(personId) != null) {
            projectList = projectService.getProjectsByPersonID(personId);
            return new ResponseEntity<>(projectList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(projectList, HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("isPerson(#personId) || isAdmin()")
    @PostMapping ("/person")
    public ResponseEntity<Project> saveProject(@RequestParam(value = "id") Long personId, @RequestBody Project projectReq) {
        Person p = personService.findPerson(personId);
        projectReq.setPerson(p);
        Project newProject = projectService.saveProject(projectReq);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteProject(@RequestParam(value = "id") Long id,
            @AuthenticationPrincipal UserSecurity user) {
        
        Person p = personService.findPerson(user.getPerson().getId());
        Project project = projectService.findProject(id);
        if (! p.getProjects().contains(project) && p.getId() != 1){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        projectService.deleteProject(id) ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<Project> editProject(@RequestParam(value= "id") Long id, @RequestBody Project projectReq,
            @AuthenticationPrincipal UserSecurity user){
        
        Project project = projectService.findProject(id);
		Person p = user.getPerson();
        if (project.getPerson().getId() != p.getId() && p.getId() != 1){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        projectReq.setPerson(project.getPerson());
        projectReq.setId(project.getId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(projectReq, project);

        return new ResponseEntity<>(projectService.saveProject(project), HttpStatus.OK);

    }
}
