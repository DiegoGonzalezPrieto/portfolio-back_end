package com.argentinaprograma.portfolio.repository;

import com.argentinaprograma.portfolio.model.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISkillRepo extends JpaRepository<Skill, Long> {
    
    public List<Skill> findAllByPersonId(Long personId);

}
