package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.City;
import com.argentinaprograma.portfolio.service.ICityService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private ICityService cityService;
    
    @GetMapping("/testId")
    public boolean cityExists(@RequestParam(value = "id") Long id){
        return cityService.cityExistsById(id);
    }
    
    @GetMapping("/testNames")
    public boolean cityExistsByNames(@RequestParam(value = "city")String cityName, @RequestParam(value = "country")String countryName){
        return cityService.cityExistsByNames(cityName, countryName);
    }
    
    @GetMapping("/testNamesReturn")
    public City getCityByNames(@RequestParam(value = "city")String cityName, @RequestParam(value = "country")String countryName){
        return cityService.findCityByNames(cityName, countryName);
    }
    
    
    @GetMapping ("/get/all")
    public ResponseEntity<List<City>> getCitys() {
        List<City> cities = cityService.getCitys();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
    
    @GetMapping ("/get")
    public ResponseEntity<City> getCity(@RequestParam(value = "id") Long id) {
        City city = cityService.findCity(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/create")
    public ResponseEntity<City> saveCity(@RequestBody City city) {
        City newCity = cityService.saveCity(city);
        return new ResponseEntity<>(newCity, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteCity(@RequestParam(value = "id") Long id) {
        cityService.deleteCity(id) ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/edit")
    public ResponseEntity<City> editCity(@RequestParam(value= "id") Long id, @RequestBody City cityReq){
        City city = cityService.findCity(id);
        cityReq.setId(city.getId());
        
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(cityReq, city);

        return new ResponseEntity<>(cityService.saveCity(city), HttpStatus.OK);
    }
}