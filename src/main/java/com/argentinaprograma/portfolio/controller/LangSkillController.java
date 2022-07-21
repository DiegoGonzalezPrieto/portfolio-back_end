package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.Person;
import com.argentinaprograma.portfolio.model.LangSkill;
import com.argentinaprograma.portfolio.model.Language;
import com.argentinaprograma.portfolio.security.model.UserSecurity;
import com.argentinaprograma.portfolio.service.ILangSkillService;
import com.argentinaprograma.portfolio.service.ILanguageService;
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
@RequestMapping("/lSkill")
public class LangSkillController {

    @Autowired
    IPersonService personService;
    
    @Autowired
    ILanguageService langService;

    @Autowired
    private ILangSkillService langSkillService;
    
    @GetMapping ("/get/all")
    public ResponseEntity<List<LangSkill>> getLangSkills() {
        List<LangSkill> lSkills = langSkillService.getLangSkills();
        return new ResponseEntity<>(lSkills, HttpStatus.OK);
    }
    
    @GetMapping ("/get")
    public ResponseEntity<LangSkill> getLangSkill(@RequestParam(value = "id") Long id) {
        LangSkill lSkill = langSkillService.findLangSkill(id);
        return new ResponseEntity<>(lSkill, HttpStatus.OK);
    }

    @GetMapping ("/person")
    public ResponseEntity<List<LangSkill>> getLangSkillsByPersonId(@RequestParam(value = "id") Long personId) {
        List<LangSkill> langSkillList = new ArrayList();
        if (personService.findPerson(personId) != null) {
            langSkillList = langSkillService.getLangSkillsByPersonID(personId);
            return new ResponseEntity<>(langSkillList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(langSkillList, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isPerson(#personId) || isAdmin()")
    @PostMapping ("/person")
    public ResponseEntity<LangSkill> saveLangSkill(@RequestParam(value = "id") Long personId, @RequestBody LangSkill lSkillReq) {
        Person p = personService.findPerson(personId);
        lSkillReq.setPerson(p);
        
        // check for language in request
        if (lSkillReq.getLanguage() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
         // check existing language or persist it
         Language language = lSkillReq.getLanguage();
         if (language.getId() != null && langService.findLanguage(language.getId()) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            
        } else if (language.getId() == null && language.getName() != null) {
            if (langService.findByName(language.getName()) == null) {
                Language savedLang = langService.saveLanguage(language);
                lSkillReq.setLanguage(savedLang);
            } else if (langService.findByName(language.getName()) != null){
                lSkillReq.setLanguage(langService.findByName(language.getName()));
            }
        }
        LangSkill newLangSkill = langSkillService.saveLangSkill(lSkillReq);
        return new ResponseEntity<>(newLangSkill, HttpStatus.CREATED);
    }

        
    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteLangSkill(@RequestParam(value = "id") Long id,
            @AuthenticationPrincipal UserSecurity user) {
        Person p = personService.findPerson(user.getPerson().getId());
        LangSkill ls = langSkillService.findLangSkill(id);

        if (! p.getLanguages().contains(ls) && p.getId() != 1){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        langSkillService.deleteLangSkill(id) ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<LangSkill> editLangSkill(@RequestParam(value= "id") Long id, @RequestBody LangSkill lSkillReq,
            @AuthenticationPrincipal UserSecurity user){
        LangSkill lSkill = langSkillService.findLangSkill(id);
		Person p = user.getPerson();
        
        if (lSkill.getPerson().getId() != p.getId() && p.getId() != 1){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        lSkillReq.setPerson(lSkill.getPerson());
        lSkillReq.setId(lSkill.getId());
        
        // create new language if not presisted and only name is given
        if (lSkillReq.getLanguage() != null && lSkillReq.getLanguage().getId() == null){
            Language language = lSkillReq.getLanguage();
            if (langService.findByName(language.getName()) == null){
                System.out.println("#### Language NOT found by name ###");
                Language savedLang = langService.saveLanguage(language);
                lSkillReq.setLanguage(savedLang);
            } else if (langService.findByName(language.getName()) != null){
                System.out.println("#### Language found by name :)  ###");
                Language savedLang = langService.saveLanguage(langService.findByName(language.getName()));
                lSkillReq.setLanguage(savedLang);
            }
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(lSkillReq, lSkill);
        return new ResponseEntity<>(langSkillService.saveLangSkill(lSkill), HttpStatus.OK);

    }
}
