package ru.dilgorp.shmock.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.dilgorp.shmock.configurations.RabbitTestContainerInitializer;

@SpringBootTest
@ContextConfiguration(initializers = {
        RabbitTestContainerInitializer.class
})
public abstract class BaseStreamTest {
}
