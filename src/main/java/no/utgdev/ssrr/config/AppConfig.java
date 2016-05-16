package no.utgdev.ssrr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ApiConfig.class
})
public class AppConfig {
    @Bean
    public String test() {
        return "";
    }
}
