package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.Skill;
import java.util.List;

public interface ISkillService {

    // get all skill
    public List<Skill> getSkills();

    // create skill
    public Skill saveSkill(Skill skill);

    // delete skill
    public boolean deleteSkill(Long id);

    // find skill
    public Skill findSkill(Long id);

    // find all Skill by personId
    public List<Skill> getSkillsByPersonID(Long personId);
}
