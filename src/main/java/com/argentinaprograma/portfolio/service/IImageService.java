package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.Image;
import java.util.List;

public interface IImageService {

    // get all image
    public List<Image> getImages();

    // create image
    public Image saveImage(Image image);

    // delete image
    public boolean deleteImage(Long id);

    // find image
    public Image findImage(Long id);


}
