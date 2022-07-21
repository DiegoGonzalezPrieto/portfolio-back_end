package com.argentinaprograma.portfolio.security.model;

import com.argentinaprograma.portfolio.model.Person;
import com.sun.istack.NotNull;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    @Column(unique = true)
    private String userName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns= @JoinColumn(name= "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotNull
    private Set<Role> roles = new HashSet<Role>();
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_person", joinColumns= @JoinColumn(name= "user_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @NotNull
    private Person person;
    
    
    

    public User(String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this. person = new Person();
    }
    
    
}
