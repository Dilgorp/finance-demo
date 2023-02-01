package ru.dilgorp.finance.rabbit.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;

import java.util.function.Consumer;

@Component("sh-events-consumer")
@Slf4j
public class ShEventConsumer implements Consumer<AbstractMessage<?>> {
    @Override
    public void accept(AbstractMessage<?> message) {
        log.info("Received message sh event: {}", message);
    }
}
