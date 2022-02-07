package ru.halmg.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "ru.halmg.service")
@PropertySource("classpath:application.properties")
public class ServiceConfig {

    @Value("${elasticsearch.host}")
    private String elasticHost;
    @Value("${elasticsearch.port}")
    private int elasticPort;

    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost(elasticHost, elasticPort, "http")));
    }
}
