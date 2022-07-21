package com.argentinaprograma.portfolio.repository;

import com.argentinaprograma.portfolio.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepo extends JpaRepository<Person, Long> {

}
