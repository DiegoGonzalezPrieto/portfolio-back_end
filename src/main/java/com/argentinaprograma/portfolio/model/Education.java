package com.argentinaprograma.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Year;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "educations")
public class Education {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String institution;
    private Year startYear;
    @Nullable
    private Year endYear;
    private boolean current;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;
    
    @Enumerated(EnumType.STRING)
    private EduLevel level;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Person person;

}
