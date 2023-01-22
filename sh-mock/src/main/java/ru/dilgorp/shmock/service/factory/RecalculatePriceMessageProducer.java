package ru.dilgorp.shmock.service.factory;

import org.springframework.stereotype.Component;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.domain.message.RecalculatePriceMessage;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class RecalculatePriceMessageProducer implements MessageProducer<RecalculatePricePayload>{

    private final Random random = new Random();

    @Override
    public Class<RecalculatePricePayload> payloadClass() {
        return RecalculatePricePayload.class;
    }

    @Override
    public AbstractMessage<RecalculatePricePayload> produce(long dealId, long seq) {
        Service[] services = Service.values();
        var service = services[random.nextInt(services.length)];

        return new RecalculatePriceMessage(
                LocalDateTime.now(),
                dealId, seq, new RecalculatePricePayload(List.of(service))
        );
    }
}
