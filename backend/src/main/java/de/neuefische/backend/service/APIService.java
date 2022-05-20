package de.neuefische.backend.service;

import de.neuefische.backend.model.NasaPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class APIService {

    private final WebClient webClient;


    @Autowired
    public APIService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Value("${neuefische.capstone.nasa.api.key}")
    private String API_KEY;


    public NasaPicture getPictureOfTheDay() {
        //API Call
        NasaPicture nasaPicture = webClient
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
