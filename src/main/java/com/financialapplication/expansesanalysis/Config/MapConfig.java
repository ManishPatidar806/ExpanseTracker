package com.financialapplication.expansesanalysis.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapConfig {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();

    }
}
