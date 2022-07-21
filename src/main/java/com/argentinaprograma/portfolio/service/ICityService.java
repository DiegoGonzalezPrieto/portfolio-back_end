package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.City;
import java.util.List;

public interface ICityService {

    // get all city
    public List<City> getCitys();

    // create city
    public City saveCity(City city);

    // delete city
    public boolean deleteCity(Long id);

    // find city
    public City findCity(Long id);
    
    //find by city name
    public City findByCityName(String cityName);
    
    //check exists by Id
    public boolean cityExistsById(Long id);
    
    // check exists by name and country
    boolean cityExistsByNames(String cityName, String countryName);
    
    // find by city and country
    City findCityByNames(String cityName, String countryName);

}
