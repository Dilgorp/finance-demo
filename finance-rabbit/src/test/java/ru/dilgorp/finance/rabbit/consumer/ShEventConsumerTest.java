package ru.dilgorp.finance.rabbit.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.message.RecalculatePriceMessage;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;
import ru.dilgorp.finance.rabbit.base.BaseStreamTest;
import ru.dilgorp.finance.rabbit.interceptor.TestMessageInterceptor;
import ru.dilgorp.finance.rabbit.producer.TestShEventsProducer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
class ShEventConsumerTest extends BaseStreamTest {

    @Autowired
    private ShEventConsumer shEventConsumer;

    @Autowired
    private TestShEventsProducer testShEventsProducer;

    @Test
    public void accept_happyPath() throws InterruptedException {
        var message = new RecalculatePriceMessage(
                LocalDateTime.now(),
                1, 1, new RecalculatePricePayload(List.of(Service.SBR))
        );

        var testMessageInterceptor = new TestMessageInterceptor();
        shEventConsumer.addMessageInterceptor(testMessageInterceptor);

        testMessageInterceptor.resetLatch();
        testShEventsProducer.send(message);

        var messageConsumed = testMessageInterceptor.getLatch().await(10, TimeUnit.SECONDS);

        assertTrue(messageConsumed);
        assertEquals(message, testMessageInterceptor.getMessage());
    }
}