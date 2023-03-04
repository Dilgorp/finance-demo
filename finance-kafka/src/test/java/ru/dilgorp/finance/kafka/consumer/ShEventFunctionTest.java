package ru.dilgorp.finance.kafka.consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.message.RecalculatePriceMessage;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;
import ru.dilgorp.finance.kafka.base.BaseStreamTest;
import ru.dilgorp.finance.kafka.interceptor.TestMessageInterceptor;
import ru.dilgorp.finance.kafka.producer.TestShEventsProducer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShEventFunctionTest extends BaseStreamTest {
    @Autowired
    private ShEventFunction shEventFunction;

    @Autowired
    private TestMessageInterceptor testMessageInterceptor;

    @Autowired
    private TestShEventsProducer testShEventsProducer;

    @Test
    public void apply_consume_happyPath() throws InterruptedException {
        var message = new RecalculatePriceMessage(
                LocalDateTime.now(),
                1, 1, new RecalculatePricePayload(List.of(Service.SBR))
        );

        testMessageInterceptor.resetLatch();
        testShEventsProducer.send(message);

        var messageConsumed = testMessageInterceptor.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertEquals(message, testMessageInterceptor.getMessage());
    }

    @ParameterizedTest
    @MethodSource("apply_produce_source")
    public void apply_produce_happyPath(Long dealId, Long partitionKey) {
        var message = new RecalculatePriceMessage(
                LocalDateTime.now(),
                dealId, 1, new RecalculatePricePayload(List.of(Service.SBR))
        );

        var result = shEventFunction.apply(message);

        assertEquals(message, result.getPayload());
        assertEquals(partitionKey, result.getHeaders().get("partitionKey"));
    }

    public static Stream<Arguments> apply_produce_source(){
        return Stream.of(
                Arguments.of(0L, 0L),
                Arguments.of(1L, 1L),
                Arguments.of(2L, 2L),
                Arguments.of(3L, 3L),
                Arguments.of(4L, 0L),
                Arguments.of(5L, 1L),
                Arguments.of(6L, 2L),
                Arguments.of(7L, 3L),
                Arguments.of(8L, 0L),
                Arguments.of(9L, 1L)
        );
    }
}