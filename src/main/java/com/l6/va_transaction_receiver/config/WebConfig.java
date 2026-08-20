package com.l6.va_transaction_receiver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // application.yaml eken read karanawa:
    //   app.cors.allowed-origins: "*"   -> ANY origin ekak allow karanawa
    //   app.cors.allowed-origins: "http://localhost:5173,https://your-app.netlify.app"  -> specific origins witharak
    @Value("${app.cors.allowed-origins}")
    private String allowedOriginsRaw;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = allowedOriginsRaw.split(",");

        registry.addMapping("/**")          // ඔක්කොම paths cover කරනවා
                .allowedOrigins(origins)    // "*" dunnoth ANY origin ekak accept karanawa
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)    // SSE uses EventSource -> credentials false
                .maxAge(3600);
    }
}