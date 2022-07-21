package com.argentinaprograma.portfolio.service;



import com.argentinaprograma.portfolio.model.Person;
import com.argentinaprograma.portfolio.repository.IPersonRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private IPersonRepo personRepo;

    @Override
    public List<Person> getPersons() {
        List<Person> p = personRepo.findAll();
        return p;
    }

    @Override
    public Person savePerson(Person person) {
        return personRepo.save(person);
    }

    @Override
    public boolean deletePerson(Long id) {
        try {
            personRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Person findPerson(Long id) {
        Person p = personRepo.findById(id).orElse(null);
        return p;
    }

}
