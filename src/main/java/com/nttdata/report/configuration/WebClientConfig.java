package com.nttdata.report.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for setting up a WebClient bean.
 */
@Configuration
public class WebClientConfig {
    @Value("${server.url.webclient}")
    private String webclientUrl;
    /**
     * Creates and configures a WebClient bean.
     *
     * @return a configured WebClient instance
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(webclientUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }



}
