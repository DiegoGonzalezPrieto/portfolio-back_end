package com.argentinaprograma.portfolio.service;



import com.argentinaprograma.portfolio.model.Education;
import com.argentinaprograma.portfolio.repository.IEducationRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationService implements IEducationService {

    @Autowired
    private IEducationRepo eduRepo;

    @Override
    public List<Education> getEducations() {
        List<Education> edus = eduRepo.findAll();
        return edus;
    }

    @Override
    public Education saveEducation(Education education) {
        return eduRepo.save(education);
    }

    @Override
    public boolean deleteEducation(Long id) {
        try {
            eduRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Education findEducation(Long id) {
        Education edu = eduRepo.findById(id).orElse(null);
        return edu;
    }

    @Override
    public List<Education> getEducationsByPersonID(Long personId) {
        return eduRepo.findAllByPersonId(personId);
    }
}
