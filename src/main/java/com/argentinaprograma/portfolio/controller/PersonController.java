package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.City;
import com.argentinaprograma.portfolio.model.LangSkill;
import com.argentinaprograma.portfolio.model.Person;
import com.argentinaprograma.portfolio.service.ICityService;
import com.argentinaprograma.portfolio.service.IPersonService;
import com.sun.istack.NotNull;
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
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private IPersonService personService;
    
    @Autowired
    private ICityService cityService;
    
    
    @GetMapping ("/get/all")
    public ResponseEntity<List<Person>> getPersons() {
        List<Person> persons = personService.getPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }
    
    @GetMapping ("/get")
    public ResponseEntity<Person> getPerson(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(personService.findPerson(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/create")
    public Person savePerson(@RequestBody Person person) {
        
        // city check or create
        City cityReq = person.getCity();
        if (cityReq != null){
            if (cityReq.getId() != null && cityService.cityExistsById(cityReq.getId())){
                person.setCity(cityService.findCity(cityReq.getId()));
            } else if (cityService.cityExistsByNames(cityReq.getCityName(), cityReq.getCountryName())){
                person.setCity(cityService.findCityByNames(cityReq.getCityName(), cityReq.getCountryName()));
            } else if (cityReq.getCityName() != null && cityReq.getCountryName() != null) {
                cityReq.setId(null);
                person.setCity(cityService.saveCity(cityReq));
            } else {
                person.setCity(null);
            }
        }
        return personService.savePerson(person);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/delete")
    public String deletePerson(@RequestParam(value = "id") Long id) {
        if (personService.deletePerson(id)){
            return "Se eliminó a la persona de id " + id + ".";
        } else {
            return "La persona no existe o no pudo ser eliminada";
        }
    }

    @PreAuthorize("isPerson(#id) || isAdmin()")
    @PutMapping ("/edit")
    public ResponseEntity<Person> editPerson(@RequestParam(value= "id") Long id, @RequestBody Person personReq){
        Person person = personService.findPerson(id);
        personReq.setId(person.getId());
        
        // city check or create
        City cityReq = personReq.getCity();
        if (cityReq != null){
            if (cityReq.getId() != null && cityService.cityExistsById(cityReq.getId())){
                personReq.setCity(cityService.findCity(cityReq.getId()));
            } else if (cityService.cityExistsByNames(cityReq.getCityName(), cityReq.getCountryName())){
                personReq.setCity(cityService.findCityByNames(cityReq.getCityName(), cityReq.getCountryName()));
            } else if (cityReq.getCityName() != null && cityReq.getCountryName() != null) {
                personReq.setCity(cityService.saveCity(cityReq));
            } else {
                personReq.setCity(null);
            }   
        }
        
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(personReq, person);

        return new ResponseEntity<>(personService.savePerson(person), HttpStatus.OK);
    }
}
