package com.baydinoglu.widgets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableCaching
public class WidgetsApplication implements WebMvcConfigurer {

    public static void main(final String[] args) {
        SpringApplication.run(WidgetsApplication.class, args);
    }
}
