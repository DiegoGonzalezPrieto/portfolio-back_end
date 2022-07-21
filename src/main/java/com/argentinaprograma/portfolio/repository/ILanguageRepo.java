package com.argentinaprograma.portfolio.repository;

import com.argentinaprograma.portfolio.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILanguageRepo extends JpaRepository<Language, Long> {
    Language findByName(String name);
}
