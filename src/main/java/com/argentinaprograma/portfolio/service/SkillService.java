package com.argentinaprograma.portfolio.service;



import com.argentinaprograma.portfolio.model.Skill;
import com.argentinaprograma.portfolio.repository.ISkillRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService implements ISkillService {

    @Autowired
    private ISkillRepo skillRepo;

    @Override
    public List<Skill> getSkills() {
        List<Skill> s = skillRepo.findAll();
        return s;
    }

    @Override
    public Skill saveSkill(Skill skill) {
        return skillRepo.save(skill);
    }

    @Override
    public boolean deleteSkill(Long id) {
        try {
            skillRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Skill findSkill(Long id) {
        Skill s = skillRepo.findById(id).orElse(null);
        return s;
    }

    @Override
    public List<Skill> getSkillsByPersonID(Long personId) {
        return skillRepo.findAllByPersonId(personId);
    }

}
