package ru.dilgorp.shmock.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class MessageFactory {

    private final Map<Class<?>, MessageProducer<?>> producersMap;
    private final List<Class<?>> payloads;

    @Autowired
    public MessageFactory(List<MessageProducer<?>> producers) {
        producersMap = producers.stream().collect(
                Collectors.toMap(MessageProducer::payloadClass, messageProducer -> messageProducer));

        payloads = producersMap.keySet().stream().toList();
    }

    public List<AbstractMessage<?>> produce(int partitionSize, long dealId, long seq) {
        var random = new Random();

        var messages = new ArrayList<AbstractMessage<?>>(partitionSize);
        for (int i = 0; i < partitionSize; i++) {
            var payloadIndex = random.nextInt(2);
            messages.add(producersMap.get(payloads.get(payloadIndex)).produce(dealId, seq));
        }

        return messages;
    }
}
