package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.entity.Images;
import com.hms.entity.Property;
import com.hms.repository.ImagesRepository;
import com.hms.repository.PropertyRepository;
import com.hms.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private BucketService bucketService;
    private PropertyRepository propertyRepository;
    private ImagesRepository imagesRepository;
    public ImageController(BucketService bucketService,PropertyRepository propertyRepository,ImagesRepository imagesRepository) {
        this.bucketService = bucketService;
        this.propertyRepository = propertyRepository;
        this.imagesRepository=imagesRepository;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadPropertyPhotos(@RequestParam MultipartFile file,
                                        @PathVariable String bucketName,
                                        @PathVariable long propertyId,
                                        @AuthenticationPrincipal AppUser user
    ) {
        Property property = propertyRepository.findById(propertyId).get();

        String imageUrl = bucketService.uploadFile(file, bucketName);
        Images image = new Images();
        image.setUrl(imageUrl);
        image.setProperty(property);
        Images savedImage = imagesRepository.save(image);
        return new ResponseEntity<>(savedImage, HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Images>> getAllImages(
            @PathVariable long propertyId

    ) {
        Property property = propertyRepository.findById(propertyId).get();
        List<Images> propertyImages = imagesRepository.findByProperty(property);
        return new ResponseEntity<>(propertyImages,HttpStatus.OK);
    }
}
