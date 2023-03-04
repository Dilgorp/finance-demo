package ru.dilgorp.finance.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;

@Component
@RequiredArgsConstructor
public class TestShEventsProducer {
    private static final String OUTPUT = "sh-events-test-out-0";

    private final StreamBridge streamBridge;

    public void send(AbstractMessage<?> message) {
        streamBridge.send(
                OUTPUT,
                MessageBuilder.withPayload(message)
                        .build()
        );
    }
}