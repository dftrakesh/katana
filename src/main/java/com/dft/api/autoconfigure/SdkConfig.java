package com.dft.api.autoconfigure;

import com.dft.api.KatanaSDK;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SdkConfig {

    @Bean
    public KatanaSDK katanaSDK() {
        return new KatanaSDK("eba290d0-4adc-497d-a201-33427b3145ca");
    }
}