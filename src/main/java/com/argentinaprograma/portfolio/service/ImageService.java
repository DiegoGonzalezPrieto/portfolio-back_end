package com.argentinaprograma.portfolio.service;



import com.argentinaprograma.portfolio.model.Image;
import com.argentinaprograma.portfolio.repository.IImageRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService implements IImageService {

    @Autowired
    private IImageRepo imageRepo;

    @Override
    public List<Image> getImages() {
        List<Image> imgs = imageRepo.findAll();
        return imgs;
    }

    @Override
    public Image saveImage(Image image) {
        return imageRepo.save(image);
    }

    @Override
    public boolean deleteImage(Long id) {
        try {
            imageRepo.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public Image findImage(Long id) {
        Image img = imageRepo.findById(id).orElse(null);
        return img;
    }

}
