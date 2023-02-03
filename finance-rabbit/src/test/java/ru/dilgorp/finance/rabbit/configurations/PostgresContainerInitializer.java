package ru.dilgorp.finance.rabbit.configurations;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainerProvider;

public class PostgresContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static JdbcDatabaseContainer<?> postgresSQLContainer = new PostgreSQLContainerProvider()
            .newInstance("latest")
            .withDatabaseName("finance-rabbit-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        postgresSQLContainer.start();

        TestPropertyValues.of(
                "spring.datasource.url=" + postgresSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgresSQLContainer.getUsername(),
                "spring.datasource.password=" + postgresSQLContainer.getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}
