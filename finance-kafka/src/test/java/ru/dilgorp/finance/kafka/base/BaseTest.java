package ru.dilgorp.finance.kafka.base;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.dilgorp.finance.kafka.client.ShMockClientApi;
import ru.dilgorp.finance.kafka.configurations.KafkaTestContainersInitializer;
import ru.dilgorp.finance.kafka.configurations.PostgresContainerInitializer;
import ru.dilgorp.finance.kafka.configurations.RabbitTestContainerInitializer;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {
        PostgresContainerInitializer.class,
        RabbitTestContainerInitializer.class,
        KafkaTestContainersInitializer.class
})
public abstract class BaseTest {
    @MockBean
    private ShMockClientApi shMockClientApi;

    @BeforeEach
    public void setUp() {
        when(shMockClientApi.longTime()).thenReturn(1000L);
    }
}
