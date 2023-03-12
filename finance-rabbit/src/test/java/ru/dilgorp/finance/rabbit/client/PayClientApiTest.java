package ru.dilgorp.finance.rabbit.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.ContextConfiguration;
import ru.dilgorp.domain.dto.PayPaymentDto;
import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.finance.rabbit.base.BaseTest;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(
        initializers = {PayClientApiTest.WireMockInitializer.class}
)
class PayClientApiTest extends BaseTest {

    @Autowired
    private PayClientApi payClientApi;

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
                    .of("feign.pay-client.base-url=http://localhost:" + wireMockServer.port())
                    .applyTo(applicationContext.getEnvironment());
        }
    }

    @Test
    @SneakyThrows
    public void getPayment_happyPath() {
        var externalId = UUID.randomUUID();
        var dto = new PayPaymentDto(1L, externalId, PayPaymentStatus.PAID);

        wireMockServer.stubFor(get(urlEqualTo("/payments/external_id/" + externalId))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(objectMapper.writeValueAsString(dto))
                )
        );

        var result = payClientApi.getPayment(externalId);

        assertEquals(dto, result);
    }

}