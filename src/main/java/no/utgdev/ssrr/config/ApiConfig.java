package no.utgdev.ssrr.config;


import org.glassfish.jersey.server.ResourceConfig;

public class ApiConfig extends ResourceConfig {
    public ApiConfig() {
        packages("no.utgdev.ssrr.api");
    }
}
