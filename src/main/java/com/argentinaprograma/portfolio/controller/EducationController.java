package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.Person;
import com.argentinaprograma.portfolio.model.Education;
import com.argentinaprograma.portfolio.security.model.UserSecurity;
import com.argentinaprograma.portfolio.service.IEducationService;
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
@RequestMapping("/edu")
public class EducationController {

    @Autowired
    IPersonService personService;

    @Autowired
    private IEducationService eduService;
    
    @GetMapping ("/get/all")
    public ResponseEntity<List<Education>> getEducations() {
        List<Education> edus = eduService.getEducations();
        return new ResponseEntity<>(edus, HttpStatus.OK);
    }
    
    @GetMapping ("/get")
    public ResponseEntity<Education> getEducation(@RequestParam(value = "id") Long id) {
        Education edu = eduService.findEducation(id);
        return new ResponseEntity<>(edu, HttpStatus.OK);
    }

    @GetMapping ("/person")
    public ResponseEntity<List<Education>> getEducationsByPersonId(@RequestParam(value = "id") Long personId) {
        List<Education> eduList = new ArrayList();
        if (personService.findPerson(personId) != null) {
            eduList = eduService.getEducationsByPersonID(personId);
            return new ResponseEntity<>(eduList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(eduList, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isPerson(#personId)")
    @PostMapping ("/person")
    public ResponseEntity<Education> saveEducation(@RequestParam(value = "id") Long personId, @RequestBody Education eduReq) {
        Person p = personService.findPerson(personId);
        eduReq.setPerson(p);
        Education newEdu = eduService.saveEducation(eduReq);
        return new ResponseEntity<>(newEdu, HttpStatus.CREATED);
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteEducation(@RequestParam(value = "id") Long id, 
            @AuthenticationPrincipal UserSecurity user) {
        
        // check User deletes corresponding element
        
        Person p = personService.findPerson(user.getPerson().getId());
        Education e = eduService.findEducation(id);
        if (! p.getEducations().contains(e)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        eduService.deleteEducation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        
    }

    @PutMapping("/edit")
    public ResponseEntity<Education> editEducation(@RequestParam(value= "id") Long id, @RequestBody Education eduReq,
            @AuthenticationPrincipal UserSecurity user){
        
        Education edu = eduService.findEducation(id);
        
        if (edu.getPerson().getId() != user.getPerson().getId()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        eduReq.setPerson(edu.getPerson());
        eduReq.setId(edu.getId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(eduReq, edu);

        return new ResponseEntity<>(eduService.saveEducation(edu), HttpStatus.OK);

    }
}