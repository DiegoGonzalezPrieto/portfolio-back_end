package com.argentinaprograma.portfolio.repository;

import com.argentinaprograma.portfolio.model.WorkExp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkExpRepo extends JpaRepository<WorkExp, Long> {
    
    public List<WorkExp> findAllByPersonId(Long personId);

}
