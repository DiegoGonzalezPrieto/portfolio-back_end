package com.argentinaprograma.portfolio.repository;

import com.argentinaprograma.portfolio.model.ContactInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactInfoRepo extends JpaRepository<ContactInfo, Long> {
    
    public List<ContactInfo> findAllByPersonId(Long personId);

}
