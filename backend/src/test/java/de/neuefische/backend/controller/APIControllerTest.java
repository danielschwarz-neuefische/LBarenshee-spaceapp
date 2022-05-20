package de.neuefische.backend.controller;

import de.neuefische.backend.model.NasaPicture;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class APIControllerTest {

    public static MockWebServer mockApi;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("neuefische.capstone.nasa.api.url", () -> mockApi.url("/").toString());
    }

    @BeforeAll
    static void setUp() throws IOException {
        mockApi = new MockWebServer();
        mockApi.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockApi.shutdown();
    }

    @Autowired
    private WebTestClient testClient;

    @Test
    void getPictureOfTheDay() {
        //GIVEN
        mockApi.enqueue(
                new MockResponse()
                        .setResponseCode(HttpStatus.OK.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("""
                                {
                                    "title": "A Digital Lunar Eclipse",
                                    "date": "2022-05-19",
                                    "explanation": "Description of Picture",
                                    "copyright": "Michael Cain",
                                    "url": "https://apod.nasa.gov/apod/image/2205/TLE_2022-05-16-02-59-35s1024.jpg",
                                    "hdurl": "https://apod.nasa.gov/apod/image/2205/TLE_2022-05-16-02-59-35s.jpg",
                                    "media_type": "image",
                                    "service_version": "v1"
                                }
                                """)
        );

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
                .title("A Digital Lunar Eclipse")
                .date("2022-05-19")
                .explanation("Description of Picture")
                .copyright("Michael Cain")
                .url("https://apod.nasa.gov/apod/image/2205/TLE_2022-05-16-02-59-35s1024.jpg")
                .hdurl("https://apod.nasa.gov/apod/image/2205/TLE_2022-05-16-02-59-35s.jpg")
                .mediaType("image")
                .serviceVersion("v1")
                .build();
        assertEquals(expected, actual);
        System.out.println(actual);
    }
}