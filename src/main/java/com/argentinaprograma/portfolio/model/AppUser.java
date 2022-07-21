package com.argentinaprograma.portfolio.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO : replace with security User Class?
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AppUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
    
}
