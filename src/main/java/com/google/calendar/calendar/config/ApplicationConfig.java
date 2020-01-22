package com.google.calendar.calendar.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


@Configuration
@EnableR2dbcRepositories
public class ApplicationConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.r2dbc.host}")
    private String HOST;

    @Value("${spring.r2dbc.port}")
    private Integer PORT;

    @Value("${spring.r2dbc.username}")
    private String USERNAME;

    @Value("${spring.r2dbc.password}")
    private String PASSWORD;

    @Value("${spring.r2dbc.database}")
    private String DATABASE;

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host(HOST)
                .port(PORT)
                .username(USERNAME)
                .password(PASSWORD)
                .database(DATABASE)
                .build());
    }

    @Bean
    public ConnectionFactoryInitializer initializer() {
        final ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory());

        final CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("sql/create_tables.sql")));
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

}
