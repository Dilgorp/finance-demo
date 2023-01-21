package ru.dilgorp.kafkademo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.SimpleMessage;

import java.util.function.Consumer;

@Component("consumer-rabbit")
@Slf4j
public class RabbitMessageConsumer extends AbstractMessageConsumer implements Consumer<SimpleMessage> {

    @Override
    public void accept(SimpleMessage simpleMessage) {
        log.info("RABBIT: Received message: {}", simpleMessage);
        saveMessage(simpleMessage);
    }
}
