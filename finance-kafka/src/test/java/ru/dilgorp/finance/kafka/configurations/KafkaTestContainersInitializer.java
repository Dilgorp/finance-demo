package ru.dilgorp.finance.kafka.configurations;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaTestContainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static KafkaContainer KAFKA_CONTAINER = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        KAFKA_CONTAINER.start();
        TestPropertyValues
                .of("spring.cloud.stream.kafka.binder.brokers=" + KAFKA_CONTAINER.getBootstrapServers())
                .applyTo(applicationContext);
    }
}
