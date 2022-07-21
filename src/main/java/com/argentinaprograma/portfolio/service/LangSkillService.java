package com.argentinaprograma.portfolio.service;



import com.argentinaprograma.portfolio.model.LangSkill;
import com.argentinaprograma.portfolio.repository.ILangSkillRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LangSkillService implements ILangSkillService {

    @Autowired
    private ILangSkillRepo langSkillRepo;

    @Override
    public List<LangSkill> getLangSkills() {
        List<LangSkill> lskills = langSkillRepo.findAll();
        return lskills;
    }

    @Override
    public LangSkill saveLangSkill(LangSkill langSkill) {
        return langSkillRepo.save(langSkill);
    }

    @Override
    public boolean deleteLangSkill(Long id) {
        try {
            langSkillRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    @Override
    public LangSkill findLangSkill(Long id) {
        LangSkill ls = langSkillRepo.findById(id).orElse(null);
        return ls;
    }

    @Override
    public List<LangSkill> getLangSkillsByPersonID(Long personId) {
        return langSkillRepo.findAllByPersonId(personId);
    }

}
