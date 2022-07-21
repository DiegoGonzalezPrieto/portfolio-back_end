package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.Image;
import com.argentinaprograma.portfolio.service.IImageService;
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
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private IImageService imageService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping ("/get/all")
    public ResponseEntity<List<Image>> getImages() {
        List<Image> images = imageService.getImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping ("/get")
    public ResponseEntity<Image> getImage(@RequestParam(value = "id") Long id) {
        Image image = imageService.findImage(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/create")
    public ResponseEntity<Image> saveImage(@RequestBody Image image) {
        Image newImage = imageService.saveImage(image) ;
        return new ResponseEntity<>(newImage, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteImage(@RequestParam(value = "id") Long id) {
        imageService.deleteImage(id) ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/edit")
    public ResponseEntity<Image> editImage(@RequestParam(value= "id") Long id, @RequestBody Image imageReq){
        Image image = imageService.findImage(id);
        imageReq.setId(image.getId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(imageReq, image);

        return new ResponseEntity<>(imageService.saveImage(image), HttpStatus.OK);
    }
}