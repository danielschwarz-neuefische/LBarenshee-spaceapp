package de.neuefische.backend.controller;

import de.neuefische.backend.WebClientConfig;
import de.neuefische.backend.model.NasaPicture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class APIControllerTest {

    @MockBean
    private WebClientConfig testWebClientConfig;

    @Autowired
    private WebTestClient testClient;

    @Mock
    private ExchangeFunction exchangeFunction;

    @BeforeEach
    void setUp() {
        when(testWebClientConfig.getWebClient()).thenReturn(WebClient
                .builder()
                .exchangeFunction(exchangeFunction)
                .build());
    }

    @Test
    void getPictureOfTheDay() {
        //GIVEN
        when(exchangeFunction.exchange(any())).thenReturn(
                Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body("""
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
                        .build()));
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