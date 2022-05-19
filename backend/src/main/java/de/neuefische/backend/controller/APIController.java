package de.neuefische.backend.controller;

import de.neuefische.backend.model.NasaPicture;
import de.neuefische.backend.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/picoftheday")
public class APIController {

    private final APIService apiService;

    @Autowired
    public APIController(APIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public NasaPicture getPictureOfTheDay (){
        return apiService.getPictureOfTheDay();
    }
}
