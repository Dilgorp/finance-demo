package ru.dilgorp.finance.rabbit.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.finance.rabbit.interceptor.MessageInterceptor;
import ru.dilgorp.finance.rabbit.service.PaymentService;

import java.util.List;
import java.util.function.Consumer;

@Component("sh-events-consumer")
@Slf4j
@RequiredArgsConstructor
public class ShEventConsumer implements Consumer<AbstractMessage<?>> {

    private final List<MessageInterceptor> messageInterceptors;
    private final PaymentService paymentService;
    @Override
    public void accept(AbstractMessage<?> message) {
        messageInterceptors.forEach(messageInterceptor -> messageInterceptor.intercept(message));

        paymentService.processMessage(message);
    }

    public void addMessageInterceptor(MessageInterceptor interceptor){
        messageInterceptors.add(interceptor);
    }
}
