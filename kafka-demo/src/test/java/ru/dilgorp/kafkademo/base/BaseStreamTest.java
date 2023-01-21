package ru.dilgorp.kafkademo.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.dilgorp.kafkademo.configurations.KafkaTestContainersInitializer;
import ru.dilgorp.kafkademo.configurations.RabbitTestContainerInitializer;

@SpringBootTest
@ContextConfiguration(initializers = {
        KafkaTestContainersInitializer.class,
        RabbitTestContainerInitializer.class
})
public abstract class BaseStreamTest {
}
