package ru.dilgorp.shmock.stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class Sender {

    private static final String OUTPUT = "sh-events-out-0";

    private final StreamBridge streamBridge;

    public void send(List<AbstractMessage<?>> messages) {
        log.info("Sending {} messages", messages.size());

        for (AbstractMessage<?> message : messages) {
            log.debug("Sending message: {}", message);
            streamBridge.send(
                    OUTPUT,
                    MessageBuilder.withPayload(message)
                            .build()
            );
        }
    }
}
