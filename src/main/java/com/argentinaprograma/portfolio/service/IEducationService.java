package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.Education;
import java.util.List;

public interface IEducationService {

    // get all education
    public List<Education> getEducations();

    // create education
    public Education saveEducation(Education education);

    // delete education
    public boolean deleteEducation(Long id);

    // find education
    public Education findEducation(Long id);
    
    // find all Education by personId
    public List<Education> getEducationsByPersonID(Long personId);
    
}
