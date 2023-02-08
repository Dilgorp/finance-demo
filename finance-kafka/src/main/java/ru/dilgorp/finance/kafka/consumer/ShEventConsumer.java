package ru.dilgorp.finance.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.finance.kafka.service.PaymentService;

import java.util.function.Consumer;

@Component("sh-events-consumer")
@Slf4j
@RequiredArgsConstructor
public class ShEventConsumer implements Consumer<AbstractMessage<?>> {

    private final PaymentService paymentService;
    @Override
    public void accept(AbstractMessage<?> message) {
        log.info("Received message sh event: {}", message);
        paymentService.processMessage(message);
    }
}
