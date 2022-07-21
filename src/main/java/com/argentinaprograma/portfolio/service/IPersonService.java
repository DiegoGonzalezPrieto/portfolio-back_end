package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.Person;
import java.util.List;

public interface IPersonService {

    // get all person
    public List<Person> getPersons();

    // create person
    public Person savePerson(Person person);

    // delete person
    public boolean deletePerson(Long id);

    // find person
    public Person findPerson(Long id);


}
