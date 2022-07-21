package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.Language;
import com.argentinaprograma.portfolio.service.ILanguageService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/language")
public class LanguageController {

    @Autowired
    private ILanguageService langService;
    
    @GetMapping ("/get/all")
    public ResponseEntity<List<Language>> getLanguages() {
        List<Language> languages = langService.getLanguages();
        return new ResponseEntity<>(languages, HttpStatus.OK);
    }
    
    @GetMapping ("/get")
    public ResponseEntity<Language> getLanguage(@RequestParam(value = "id") Long id) {
        Language language = langService.findLanguage(id);
        return new ResponseEntity<>(language, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/create")
    public ResponseEntity<Language> saveLanguage(@RequestBody Language language) {
        Language newLanguage = langService.saveLanguage(language) ;
        return new ResponseEntity<>(newLanguage, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteLanguage(@RequestParam(value = "id") Long id) {
        langService.deleteLanguage(id) ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/edit")
    public ResponseEntity<Language> editLanguage(@RequestParam(value= "id") Long id, @RequestBody Language langReq){
        Language language = langService.findLanguage(id);
        langReq.setId(language.getId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(langReq, language);

        return new ResponseEntity<>(langService.saveLanguage(language), HttpStatus.OK);
    }
}