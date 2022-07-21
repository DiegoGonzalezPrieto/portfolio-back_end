package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.LangSkill;
import java.util.List;

public interface ILangSkillService {

    // get all langSkill
    public List<LangSkill> getLangSkills();

    // create langSkill
    public LangSkill saveLangSkill(LangSkill langSkill);

    // delete langSkill
    public boolean deleteLangSkill(Long id);

    // find langSkill
    public LangSkill findLangSkill(Long id);

    // find all LangSkill by personId
    public List<LangSkill> getLangSkillsByPersonID(Long personId);
}
