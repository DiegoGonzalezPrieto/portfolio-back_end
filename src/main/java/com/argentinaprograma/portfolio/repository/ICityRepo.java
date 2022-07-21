package com.argentinaprograma.portfolio.repository;

import com.argentinaprograma.portfolio.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepo extends JpaRepository<City, Long> {
    City findByCityName(String cityName);
    
    boolean existsByCityNameAndCountryName(String cityName, String countryName);
    
    City findByCityNameAndCountryName(String cityName, String countryName);
    
}
