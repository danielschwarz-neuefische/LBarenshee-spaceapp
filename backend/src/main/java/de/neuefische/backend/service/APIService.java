package de.neuefische.backend.service;

import de.neuefische.backend.WebClientConfig;
import de.neuefische.backend.model.NasaPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class APIService {

    private final WebClientConfig webClientConfig;


    @Autowired
    public APIService(WebClientConfig webClientConfig) {
        this.webClientConfig = webClientConfig;
    }

    @Value("${neuefische.capstone.nasa.api.key}")
    private String API_KEY;


    public NasaPicture getPictureOfTheDay() {
        //API Call
        NasaPicture nasaPicture = webClientConfig
                .getWebClient()
                .get()
                .uri("/planetary/apod?api_key=" + API_KEY)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .toEntity(NasaPicture.class)
                .block()
                .getBody();


        return nasaPicture;
    }
}
