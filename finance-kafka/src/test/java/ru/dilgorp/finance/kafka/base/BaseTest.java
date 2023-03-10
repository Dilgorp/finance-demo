package ru.dilgorp.finance.kafka.base;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.dilgorp.finance.kafka.configurations.KafkaTestContainersInitializer;
import ru.dilgorp.finance.kafka.configurations.PostgresContainerInitializer;
import ru.dilgorp.finance.kafka.configurations.RabbitTestContainerInitializer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {
        PostgresContainerInitializer.class,
        RabbitTestContainerInitializer.class,
        KafkaTestContainersInitializer.class,
        BaseTest.WireMockInitializer.class
})
public abstract class BaseTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected WireMockServer wireMockServer;

    public static class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            var wireMockServer = new WireMockServer(
                    WireMockConfiguration.wireMockConfig().dynamicPort().dynamicHttpsPort());

            wireMockServer.start();

            applicationContext.getBeanFactory().registerSingleton("wireMock", wireMockServer);
            applicationContext.addApplicationListener(event -> {
                if (event instanceof ContextClosedEvent) {
                    wireMockServer.stop();
                }
            });

            TestPropertyValues
                    .of("feign.sh-mock-client.base-url=http://localhost:" + wireMockServer.port())
                    .applyTo(applicationContext.getEnvironment());
        }
    }

    @BeforeEach
    public void setUp(){
        wireMockServer.stubFor(get(urlEqualTo("/long_time"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("1000")
                )
        );
    }
}
