package com.senlainc.advertisementsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = {"classpath:/service.properties"}, ignoreResourceNotFound = true),
        @PropertySource(value = {"classpath:/jwt.properties"}, ignoreResourceNotFound = true)
})
public class AppConfig {

}
