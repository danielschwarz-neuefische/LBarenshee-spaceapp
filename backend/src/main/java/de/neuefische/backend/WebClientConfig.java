package de.neuefische.backend;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
@ConditionalOnProperty(name = "nasa-api-active", havingValue = "true", matchIfMissing = true)
public class WebClientConfig {
    @Bean
    public WebClient getWebClient(){
        return WebClient.create("https://api.nasa.gov");
    }

}
