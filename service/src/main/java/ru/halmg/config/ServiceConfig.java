package ru.halmg.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@ComponentScan(basePackages = "ru.halmg.service")
@PropertySource("classpath:application.properties")
public class ServiceConfig {

    @Value("${elasticsearch.host}")
    private String elasticHost;
    @Value("${elasticsearch.port}")
    private int elasticPort;
    @Value("${cluster.name}")
    private String clusterName;

    @Bean
    public Client elasticClient() throws UnknownHostException {
        Settings settings = Settings.EMPTY;
        if (!"".equals(clusterName)) {
            settings = Settings.builder().put("cluster.name", clusterName).build();
        }
        return new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticHost), elasticPort));
    }
}
