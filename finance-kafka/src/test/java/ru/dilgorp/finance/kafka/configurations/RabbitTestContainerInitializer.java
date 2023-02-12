package ru.dilgorp.finance.kafka.configurations;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

public class RabbitTestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public static RabbitMQContainer RABBIT_CONTAINER = new RabbitMQContainer(
            DockerImageName.parse("rabbitmq:3-management"))
            .withUser("user", "password")
            .withVhost("/")
            .withPermission("/", "user", ".*", ".*", ".*");

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        var rabbitPort = "25672";
        var rabbitPortHttp = "35672";

        RABBIT_CONTAINER.setExposedPorts(List.of());
        RABBIT_CONTAINER.setPortBindings(List.of(
                rabbitPort + ":5672",
                rabbitPortHttp + ":15672"
        ));
        RABBIT_CONTAINER.start();
        TestPropertyValues
                .of("spring.rabbitmq.host=localhost",
                        "spring.rabbitmq.port=" + rabbitPort)
                .applyTo(applicationContext);
    }
}
