package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.WorkExp;
import java.util.List;

public interface IWorkExpService {

    // get all workExp
    public List<WorkExp> getWorkExps();

    // create workExp
    public WorkExp saveWorkExp(WorkExp workExp);

    // delete workExp
    public boolean deleteWorkExp(Long id);

    // find workExp
    public WorkExp findWorkExp(Long id);
    
    // find all workExp by personId
    public List<WorkExp> getWorkExpsByPersonID(Long personId);

}
