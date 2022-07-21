package com.argentinaprograma.portfolio.service;



import com.argentinaprograma.portfolio.model.WorkExp;
import com.argentinaprograma.portfolio.repository.IWorkExpRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkExpService implements IWorkExpService {

    @Autowired
    private IWorkExpRepo wExpRepo;

    @Override
    public List<WorkExp> getWorkExps() {
        List<WorkExp> we = wExpRepo.findAll();
        return we;
    }

    @Override
    public WorkExp saveWorkExp(WorkExp workExp) {
        return wExpRepo.save(workExp);
    }

    @Override
    public boolean deleteWorkExp(Long id) {
        try {
            wExpRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public WorkExp findWorkExp(Long id) {
        WorkExp we = wExpRepo.findById(id).orElse(null);
        return we;
    }

    @Override
    public List<WorkExp> getWorkExpsByPersonID(Long personId) {
        return wExpRepo.findAllByPersonId(personId);
    }

}
