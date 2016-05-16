package no.utgdev.ssrr.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig extends ResourceConfig {

    public AppConfig() {
        packages("no.utgdev.ssrr.api");
    }

    @Bean(name = "testbean")
    public String test() {
        return "BeanValue";
    }

}
