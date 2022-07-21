package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.Person;
import com.argentinaprograma.portfolio.model.WorkExp;
import com.argentinaprograma.portfolio.security.model.UserSecurity;
import com.argentinaprograma.portfolio.service.IWorkExpService;
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
@RequestMapping("/wexp")
public class WorkExpController {

    @Autowired
    IPersonService personService;

    @Autowired
    private IWorkExpService workExpService;
    
    @GetMapping ("/get/all")
    public ResponseEntity<List<WorkExp>> getWorkExps() {
        List<WorkExp> wexps = workExpService.getWorkExps();
        return new ResponseEntity<>(wexps, HttpStatus.OK);
    }
    
    @GetMapping ("/get")
    public ResponseEntity<WorkExp> getWorkExp(@RequestParam(value = "id") Long id) {
        WorkExp wexp = workExpService.findWorkExp(id);
        return new ResponseEntity<>(wexp, HttpStatus.OK);
    }

    @GetMapping ("/person")
    public ResponseEntity<List<WorkExp>> getWorkExpsByPersonId(@RequestParam(value = "id") Long personId) {
        List<WorkExp> workExpList = new ArrayList();
        if (personService.findPerson(personId) != null) {
            workExpList = workExpService.getWorkExpsByPersonID(personId);
            return new ResponseEntity<>(workExpList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(workExpList, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isPerson(#personId) || isAdmin()")
    @PostMapping ("/person")
    public ResponseEntity<WorkExp> saveWorkExp(@RequestParam(value = "id") Long personId, @RequestBody WorkExp workExpReq) {
        Person p = personService.findPerson(personId);
        workExpReq.setPerson(p);
        WorkExp newWorkExp = workExpService.saveWorkExp(workExpReq);
        return new ResponseEntity<>(newWorkExp, HttpStatus.CREATED);
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteWorkExp(@RequestParam(value = "id") Long id,
            @AuthenticationPrincipal UserSecurity user) {
        
        Person p = personService.findPerson(user.getPerson().getId());
        WorkExp we = workExpService.findWorkExp(id);
        if (! p.getWorkExps().contains(we) && p.getId() != 1){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        workExpService.deleteWorkExp(id) ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<WorkExp> editWorkExp(@RequestParam(value= "id") Long id, @RequestBody WorkExp workExpReq,
            @AuthenticationPrincipal UserSecurity user){
        WorkExp wexp = workExpService.findWorkExp(id);
		Person p = user.getPerson();
        
         if (wexp.getPerson().getId() != p.getId() && p.getId() != 1){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        workExpReq.setPerson(wexp.getPerson());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(workExpReq, wexp);

        return new ResponseEntity<>(workExpService.saveWorkExp(wexp), HttpStatus.OK);
    }
}
