package com.argentinaprograma.portfolio.repository;

import com.argentinaprograma.portfolio.model.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectRepo extends JpaRepository<Project, Long> {
    
    public List<Project> findAllByPersonId(Long personId);

}
