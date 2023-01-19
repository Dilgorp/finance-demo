package ru.dilgorp.kafkademo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.SimpleMessage;

import java.util.function.Consumer;

@Component("consumer")
@Slf4j
public class MessageConsumer implements Consumer<SimpleMessage> {

    @Override
    public void accept(SimpleMessage simpleMessage) {
        log.info("Received message: {}", simpleMessage);
    }
}
