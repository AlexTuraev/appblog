package org.tasks;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.tasks")
@PropertySource("classpath:application.properties")
@PropertySource("classpath:application-dev.properties")
public class WebConfig {
}
