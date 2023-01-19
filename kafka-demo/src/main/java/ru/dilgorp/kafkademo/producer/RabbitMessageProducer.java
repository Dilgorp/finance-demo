package ru.dilgorp.kafkademo.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.SimpleMessage;

@Component("producer-rabbit")
@RequiredArgsConstructor
@Slf4j
public class RabbitMessageProducer {

    private static final String OUTPUT = "producer-rabbit-out-0";

    private final StreamBridge streamBridge;

    public void send(SimpleMessage message) {
        log.info("RABBIT: Send message: {}", message);
        streamBridge.send(
                OUTPUT,
                MessageBuilder.withPayload(message)
                        .build()
        );
    }
}
