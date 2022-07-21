package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.Person;
import com.argentinaprograma.portfolio.model.ContactInfo;
import com.argentinaprograma.portfolio.security.model.UserSecurity;
import com.argentinaprograma.portfolio.service.IContactInfoService;
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
@RequestMapping("/cInfo")
public class ContactInfoController {

    @Autowired
    IPersonService personService;

    @Autowired
    private IContactInfoService cInfoService;
    
    
    @GetMapping ("/get/all")
    public ResponseEntity<List<ContactInfo>> getContactInfos() {
        List<ContactInfo> cInfos = cInfoService.getContactInfos();
        return new ResponseEntity<>(cInfos, HttpStatus.OK);
    }
    
    
    
    @GetMapping ("/get")
    public ResponseEntity<ContactInfo> getContactInfo(@RequestParam(value = "id") Long id) {
        ContactInfo cInfo = cInfoService.findContactInfo(id);
        return new ResponseEntity<>(cInfo, HttpStatus.OK);
    }

    
    @GetMapping ("/person")
    public ResponseEntity<List<ContactInfo>> getContactInfosByPersonId(@RequestParam(value = "id") Long personId) {
        List<ContactInfo> cInfoList = new ArrayList();
        if (personService.findPerson(personId) != null) {
            cInfoList = cInfoService.getContactInfosByPersonID(personId);
            return new ResponseEntity<>(cInfoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(cInfoList, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isPerson(#personId)")
    @PostMapping ("/person")
    public ResponseEntity<ContactInfo> saveContactInfo(@RequestParam(value = "id") Long personId, @RequestBody ContactInfo cInfoReq) {
        Person p = personService.findPerson(personId);
        cInfoReq.setPerson(p);
        ContactInfo newContInfo = cInfoService.saveContactInfo(cInfoReq);
        return new ResponseEntity<>(newContInfo, HttpStatus.CREATED);
    }

    
    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteContactInfo(@RequestParam(value = "id") Long id,
            @AuthenticationPrincipal UserSecurity user) {
        
        Person p = personService.findPerson(user.getPerson().getId());
        ContactInfo ci = cInfoService.findContactInfo(id);
        if (! p.getContactInfos().contains(ci)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        cInfoService.deleteContactInfo(id) ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<ContactInfo> editContactInfo(@RequestParam(value= "id") Long id, @RequestBody ContactInfo cInfoReq,
            @AuthenticationPrincipal UserSecurity user){
        ContactInfo cInfo = cInfoService.findContactInfo(id);
        
        if (cInfo.getPerson().getId() != user.getPerson().getId()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        cInfoReq.setPerson(cInfo.getPerson());
        cInfoReq.setId(cInfo.getId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(cInfoReq, cInfo);

        return new ResponseEntity<>(cInfoService.saveContactInfo(cInfo), HttpStatus.OK);

    }
}