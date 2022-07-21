package com.argentinaprograma.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lang_skills")
public class LangSkill extends PersonElement{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Language language;
    
    @Enumerated(EnumType.STRING) // ORDINAL es más económico, pero menos legible
    private LangLvl literacyLevel;
    
    @Enumerated(EnumType.STRING) // ORDINAL es más económico, pero menos legible
    private LangLvl oralLevel;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
//  @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @JsonIgnore
    private Person person;
}
