package ru.dilgorp.shmock.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;

import java.util.List;

@Component
@Slf4j
public class Sender {
    public void send(List<AbstractMessage<?>> messages) {
        for (AbstractMessage<?> message : messages) {
            log.debug("Sending message: {}", message);
        }
    }
}
