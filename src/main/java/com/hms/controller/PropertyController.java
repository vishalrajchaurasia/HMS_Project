package com.hms.controller;

import com.hms.entity.Property;
import com.hms.repository.PropertyRepository;
import com.hms.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {


    private PropertyService propertyService;

    private PropertyRepository propertyRepository;

    public PropertyController(PropertyService propertyService, PropertyRepository propertyRepository) {
        this.propertyService = propertyService;
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/search-hotels")
    public List<Property> searchHotels(
            @RequestParam String name

    ){
        List<Property> properties = propertyRepository.searchHotels(name);
        return properties;
    }
    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyService.saveProperty(property);
    }


}
