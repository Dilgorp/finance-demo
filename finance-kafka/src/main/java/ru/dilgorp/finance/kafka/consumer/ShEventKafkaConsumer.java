package ru.dilgorp.finance.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.finance.kafka.interceptor.HeadersInterceptor;
import ru.dilgorp.finance.kafka.interceptor.MessageInterceptor;
import ru.dilgorp.finance.kafka.service.PaymentService;

import java.util.List;
import java.util.function.Consumer;

@Component("sh-events-kafka-consumer")
@Slf4j
@RequiredArgsConstructor
public class ShEventKafkaConsumer implements Consumer<Message<AbstractMessage<?>>> {

    private final PaymentService paymentService;

    private final List<MessageInterceptor> messageInterceptors;
    private final List<HeadersInterceptor> headersInterceptors;

    @Override
    public void accept(Message<AbstractMessage<?>> message) {
        MessageHeaders headers = message.getHeaders();
        headersInterceptors.forEach(headersInterceptor -> headersInterceptor.intercept(headers));

        AbstractMessage<?> payload = message.getPayload();
        messageInterceptors.forEach(messageInterceptor -> messageInterceptor.intercept(payload));

        paymentService.processMessage(payload);
    }

    public void addMessageInterceptor(MessageInterceptor interceptor){
        messageInterceptors.add(interceptor);
    }

    public void addHeadersInterceptor(HeadersInterceptor interceptor){
        headersInterceptors.add(interceptor);
    }
}
