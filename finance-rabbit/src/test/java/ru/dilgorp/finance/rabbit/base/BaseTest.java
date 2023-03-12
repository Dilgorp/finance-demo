package ru.dilgorp.finance.rabbit.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.dilgorp.finance.rabbit.configurations.PostgresContainerInitializer;
import ru.dilgorp.finance.rabbit.configurations.RabbitTestContainerInitializer;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {
        RabbitTestContainerInitializer.class,
        PostgresContainerInitializer.class
})
public abstract class BaseTest {

    @Autowired
    protected ObjectMapper objectMapper;
}
