package com.argentinaprograma.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.argentinaprograma.portfolio.model.AppUser;

        
@Repository
public interface IAppUserRepo extends JpaRepository<AppUser, Long> {
}
