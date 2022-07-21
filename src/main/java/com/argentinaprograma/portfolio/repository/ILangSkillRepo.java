package com.argentinaprograma.portfolio.repository;

import com.argentinaprograma.portfolio.model.LangSkill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILangSkillRepo extends JpaRepository<LangSkill, Long> {
    
    public List<LangSkill> findAllByPersonId(Long personId);

}
