package no.utgdev.ssrr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "testbean")
    public String test() {
        return "BeanValue";
    }

}
