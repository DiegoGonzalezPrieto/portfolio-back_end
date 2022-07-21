package com.argentinaprograma.portfolio.repository;

import com.argentinaprograma.portfolio.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepo extends JpaRepository<Image, Long> {

}
