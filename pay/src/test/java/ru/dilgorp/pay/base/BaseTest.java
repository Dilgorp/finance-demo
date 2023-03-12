package ru.dilgorp.pay.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.dilgorp.pay.configurations.PostgresContainerInitializer;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {
        PostgresContainerInitializer.class
})
public abstract class BaseTest {
}
