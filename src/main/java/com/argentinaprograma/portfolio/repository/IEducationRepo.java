package com.argentinaprograma.portfolio.repository;

import com.argentinaprograma.portfolio.model.Education;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEducationRepo extends JpaRepository<Education, Long> {
    
    public List<Education> findAllByPersonId(Long personId);
    
}
