package ru.dilgorp.finance.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.message.RecalculatePriceMessage;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;
import ru.dilgorp.finance.kafka.base.BaseStreamTest;
import ru.dilgorp.finance.kafka.interceptor.TestHeadersInterceptor;
import ru.dilgorp.finance.kafka.interceptor.TestMessageInterceptor;
import ru.dilgorp.finance.kafka.producer.TestShEventsProducer;
import ru.dilgorp.finance.kafka.service.PaymentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@Slf4j
class ShEventKafkaConsumerTest extends BaseStreamTest {

    @Autowired
    private ShEventKafkaConsumer shEventKafkaConsumer;

    @Autowired
    private TestShEventsProducer testShEventsProducer;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void apply_consume_happyPath() throws InterruptedException {
        var message = new RecalculatePriceMessage(
                LocalDateTime.now(),
                1, 1, new RecalculatePricePayload(List.of(Service.ONREG))
        );

        log.info("TEST start apply_consume_happyPath with message={}", message);
        var testHeadersInterceptor = new TestHeadersInterceptor();
        shEventKafkaConsumer.addHeadersInterceptor(testHeadersInterceptor);

        var testMessageInterceptor = new TestMessageInterceptor();
        shEventKafkaConsumer.addMessageInterceptor(testMessageInterceptor);

        testHeadersInterceptor.resetLatch();
        testMessageInterceptor.resetLatch();

        log.info("TEST testHeadersInterceptor.getLatch().getCount()={}", testHeadersInterceptor.getLatch().getCount());
        log.info("TEST testMessageInterceptor.getLatch().getCount()={}", testMessageInterceptor.getLatch().getCount());

        testShEventsProducer.send(message);

        var messageConsumed = testHeadersInterceptor.getLatch().await(10, TimeUnit.SECONDS)
                && testMessageInterceptor.getLatch().await(10, TimeUnit.SECONDS);

        assertTrue(messageConsumed);
        assertEquals(message, testMessageInterceptor.getMessage());
        assertEquals(1L, testHeadersInterceptor.getHeaders().get("partitionKey"));
        verify(paymentService).processMessage(message);
    }
}