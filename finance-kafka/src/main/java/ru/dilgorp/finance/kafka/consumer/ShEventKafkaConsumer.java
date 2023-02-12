package ru.dilgorp.finance.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.finance.kafka.service.PaymentService;

import java.util.function.Consumer;

@Component("sh-events-kafka-consumer")
@Slf4j
@RequiredArgsConstructor
public class ShEventKafkaConsumer implements Consumer<Message<AbstractMessage<?>>> {

    private final PaymentService paymentService;

    @Override
    public void accept(Message<AbstractMessage<?>> message) {
        log.info("sh-events-kafka-consumer: received message header " + message.getHeaders());
        log.info("sh-events-kafka-consumer: received message payload " + message.getPayload());

        paymentService.processMessage(message.getPayload());
    }
}
