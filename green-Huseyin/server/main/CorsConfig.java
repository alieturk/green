package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Contains configuration for mapping certain CORS
 * settings to the endpoints in the rest controllers.
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer{

    @Value("${greenology.app.domain}")
    String allowedOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigin)
                .allowCredentials(true)
                .allowedMethods("DELETE", "PUT", "POST", "GET", "OPTIONS", "PATCH")
                .exposedHeaders(
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.EXPIRES);
    }
}