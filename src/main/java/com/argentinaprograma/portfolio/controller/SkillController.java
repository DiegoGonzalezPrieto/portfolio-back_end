package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.Person;
import com.argentinaprograma.portfolio.model.Skill;
import com.argentinaprograma.portfolio.security.model.UserSecurity;
import com.argentinaprograma.portfolio.service.ISkillService;
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
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    IPersonService personService;

    @Autowired
    private ISkillService skillService;
    
    @GetMapping ("/get/all")
    public ResponseEntity<List<Skill>> getSkills() {
        List<Skill> skills = skillService.getSkills();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }
    
    @GetMapping ("/get")
    public ResponseEntity<Skill> getSkill(@RequestParam(value = "id") Long id) {
        Skill skill = skillService.findSkill(id);
        return new ResponseEntity<>(skill, HttpStatus.OK);
    }

    
    @GetMapping ("/person")
    public ResponseEntity<List<Skill>> getSkillsByPersonId(@RequestParam(value = "id") Long personId) {
        List<Skill> skillList = new ArrayList();
        if (personService.findPerson(personId) != null) {
            skillList = skillService.getSkillsByPersonID(personId);
            return new ResponseEntity<>(skillList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(skillList, HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("isPerson(#personId)")
    @PostMapping ("/person")
    public ResponseEntity<Skill> saveSkill(@RequestParam(value = "id") Long personId, @RequestBody Skill skillReq) {
        Person p = personService.findPerson(personId);
        skillReq.setPerson(p);
        Skill newSkill = skillService.saveSkill(skillReq);
        return new ResponseEntity<>(newSkill, HttpStatus.CREATED);
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteSkill(@RequestParam(value = "id") Long id,
            @AuthenticationPrincipal UserSecurity user) {
        
        Person p = personService.findPerson(user.getPerson().getId());
        Skill s = skillService.findSkill(id);
        if (! p.getSkills().contains(s)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        skillService.deleteSkill(id) ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<Skill> editSkill(@RequestParam(value= "id") Long id, @RequestBody Skill skillReq,
        @AuthenticationPrincipal UserSecurity user){
        
        Skill skill = skillService.findSkill(id);
         if (skill.getPerson().getId() != user.getPerson().getId()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        skillReq.setPerson(skill.getPerson());
        skillReq.setId(skill.getId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(skillReq, skill);

        return new ResponseEntity<>(skillService.saveSkill(skill), HttpStatus.OK);

    }
}