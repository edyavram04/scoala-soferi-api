package ro.scoalasoferi.scoala_soferi_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Permitem accesul la toate rutele care încep cu /api/
                .allowedOrigins("http://localhost:3000") // Permitem cereri DOAR de la această adresă
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permitem aceste metode HTTP
                .allowedHeaders("*") // Permitem orice fel de headere
                .allowCredentials(true); // Permitem trimiterea de cookie-uri (dacă va fi cazul)
    }
}