package ru.dilgorp.finance.kafka.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.dilgorp.finance.kafka.configurations.PostgresContainerInitializer;
import ru.dilgorp.finance.kafka.configurations.RabbitTestContainerInitializer;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {
        RabbitTestContainerInitializer.class,
        PostgresContainerInitializer.class
})
public abstract class BaseTest {
}
