package ru.dilgorp.finance.kafka.base;

import org.springframework.test.context.ContextConfiguration;
import ru.dilgorp.finance.kafka.configurations.KafkaTestContainersInitializer;
import ru.dilgorp.finance.kafka.configurations.RabbitTestContainerInitializer;

@ContextConfiguration(initializers = {
        KafkaTestContainersInitializer.class,
        RabbitTestContainerInitializer.class
})
public abstract class BaseStreamTest extends BaseTest {
}
