package ru.halmg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ru.halmg.repository")
@PropertySource("classpath:application.properties")
public class LoaderConfig {

    @Value("${dbdriver}")
    private String dbDriver;
    @Value("${dburl}")
    private String dbUrl;
    @Value("${dbuser}")
    private String dbUser;
    @Value("${dbpassword}")
    private String dbPassword;
    @Value("${elasticsearch.host}")
    private String elasticHost;
    @Value("${elasticsearch.port}")
    private int elasticPort;
    @Value("${cluster.name}")
    private String clusterName;}
