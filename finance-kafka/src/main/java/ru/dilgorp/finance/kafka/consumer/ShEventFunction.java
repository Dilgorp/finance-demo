package ru.dilgorp.finance.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.finance.kafka.interceptor.MessageInterceptor;

import java.util.List;
import java.util.function.Function;

@Component("sh-events")
@Slf4j
@RequiredArgsConstructor
public class ShEventFunction implements Function<AbstractMessage<?>, Message<?>> {
    private final List<MessageInterceptor> messageInterceptors;

    @Override
    public Message<?> apply(AbstractMessage<?> message) {
        messageInterceptors.forEach(messageInterceptor -> messageInterceptor.intercept(message));
        var dealId = message.getDealId();
        return MessageBuilder.withPayload(message)
                .setHeader("partitionKey", dealId % 4)
                .build();
    }

    public void addMessageInterceptor(MessageInterceptor interceptor){
        messageInterceptors.add(interceptor);
    }
}
