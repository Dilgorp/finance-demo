package ru.dilgorp.finance.rabbit.configurations;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

public class RedisContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final GenericContainer<?> REDIS_CONTAINER = new GenericContainer<>(
            DockerImageName.parse("redis:7.0.9-alpine")
    );

    private static final String PORT = "36379";
    private static final String HOST = "localhost";
    private static final String DATABASE = "0";
    private static final String PASSWORD = "password";

    static {
        REDIS_CONTAINER.setPortBindings(List.of(PORT + ":6379"));
        REDIS_CONTAINER.addEnv("REDIS_PASSWORD", "password");
        REDIS_CONTAINER.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues
                .of("spring.data.redis.host=" + HOST)
                .and("spring.data.redis.port=" + PORT)
                .and("spring.data.redis.database=" + DATABASE)
                .and("spring.data.redis.password=" + PASSWORD)
                .applyTo(applicationContext.getEnvironment());
    }
}
