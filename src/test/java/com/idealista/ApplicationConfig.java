package com.idealista;

import com.idealista.service.AdsService;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

public class ApplicationConfig {

    @Bean
    public AdsService adsService() {
        return mock(AdsService.class);
    }
}
