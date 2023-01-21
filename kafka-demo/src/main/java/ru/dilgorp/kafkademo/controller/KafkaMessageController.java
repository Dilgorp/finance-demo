package ru.dilgorp.kafkademo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.domain.message.SimpleMessage;
import ru.dilgorp.kafkademo.consumer.MessageConsumer;
import ru.dilgorp.kafkademo.producer.MessageProducer;
import ru.dilgorp.kafkademo.producer.RabbitMessageProducer;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageController {

    private final MessageProducer messageProducer;
    private final MessageConsumer messageConsumer;
    private final RabbitMessageProducer rabbitMessageProducer;

    @GetMapping("/message")
    public SimpleMessage getMessage() {
        var message = new SimpleMessage(LocalDateTime.now(), "Hello world");
        log.info("Generate message: {}", message);
        messageConsumer.resetLatch();
        messageProducer.send(message);

        return message;
    }

    @GetMapping("/rabbit")
    public SimpleMessage getRabbitMessage() {
        var message = new SimpleMessage(LocalDateTime.now(), "Hello rabbit");
        log.info("RABBIT: Generate message: {}", message);

        rabbitMessageProducer.send(message);

        return message;
    }
}
