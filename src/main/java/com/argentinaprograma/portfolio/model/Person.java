package com.argentinaprograma.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persons")
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String briefCv;
    
    // Images
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image profPic;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval= true)
    private Image backPic;
    
    
    // Attributes
    @ManyToOne
    private City city;
    
    @OneToMany(mappedBy="person", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<WorkExp> workExps = new ArrayList<WorkExp>();
    
    @OneToMany( mappedBy="person", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Education> educations = new ArrayList<Education>();
    
    @OneToMany( mappedBy="person", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Project> projects = new ArrayList<Project>();
    
    @OneToMany( mappedBy="person", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Skill> skills = new ArrayList<Skill>();
    
    @OneToMany( mappedBy="person", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<LangSkill> languages = new ArrayList<LangSkill>();
    
    @OneToMany(mappedBy="person", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<ContactInfo> contactInfos = new ArrayList<ContactInfo>();
    
}
