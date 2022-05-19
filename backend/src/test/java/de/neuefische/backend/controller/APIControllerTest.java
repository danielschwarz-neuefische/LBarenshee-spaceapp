package de.neuefische.backend.controller;

import de.neuefische.backend.model.NasaPicture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class APIControllerTest {


    @Autowired
    private WebTestClient testClient;



    @Test
    void getPictureOfTheDay() {
        //WHEN
        NasaPicture actual = testClient.get()
                .uri("/picoftheday")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(NasaPicture.class)
                .returnResult()
                .getResponseBody();

        //THEN
        NasaPicture expected = NasaPicture.builder()
                .title(actual.getTitle())
                .date(actual.getDate())
                .explanation(actual.getExplanation())
                .copyright(actual.getCopyright())
                .url(actual.getUrl())
                .hdurl(actual.getHdurl())
                .mediaType(actual.getMediaType())
                .serviceVersion(actual.getServiceVersion())
                .build();
        assertEquals(expected, actual);
    }
}