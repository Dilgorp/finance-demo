package ru.dilgorp.finance.rabbit.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.ContextConfiguration;
import ru.dilgorp.finance.rabbit.base.BaseTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(
        initializers = {ShMockClientApiTest.WireMockInitializer.class}
)
class ShMockClientApiTest extends BaseTest {

    @Autowired
    private ShMockClientApi shMockClientApi;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WireMockServer wireMockServer;

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

    @Test
    public void longTime_happyPath() {
        wireMockServer.stubFor(get(urlEqualTo("/long_time"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("1000")
                )
        );

        var result = shMockClientApi.longTime();

        assertEquals(1000L, result);
    }
}