package com.argentinaprograma.portfolio.service;



import com.argentinaprograma.portfolio.model.City;
import com.argentinaprograma.portfolio.repository.ICityRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService implements ICityService {

    @Autowired
    private ICityRepo cityRepo;

    @Override
    public List<City> getCitys() {
        List<City> cities = cityRepo.findAll();
        return cities;
    }

    @Override
    public City saveCity(City city) {
        return cityRepo.save(city);
    }

    @Override
    public boolean deleteCity(Long id) {
        try {
            cityRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public City findCity(Long id) {
        City c = cityRepo.findById(id).orElse(null);
        return c;
    }

    @Override
    public City findByCityName(String cityName) {
        return cityRepo.findByCityName(cityName);
    }
    
    // city check methods
    @Override
    public boolean cityExistsById(Long id){
        return cityRepo.existsById(id);
    }

    @Override
    public boolean cityExistsByNames(String cityName, String countryName) {
        return cityRepo.existsByCityNameAndCountryName(cityName, countryName);
    }

    
    @Override
    public City findCityByNames(String cityName, String countryName) {
        return cityRepo.findByCityNameAndCountryName(cityName, countryName);
    }
    
    
}
