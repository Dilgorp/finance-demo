package ru.dilgorp.shmock.service.factory;

import org.springframework.stereotype.Component;
import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.domain.message.StatusChangedMessage;
import ru.dilgorp.domain.message.payload.StatusChangedPayload;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class StatusChangedMessageProducer implements MessageProducer<StatusChangedPayload> {

    private final Random random = new Random();

    @Override
    public Class<StatusChangedPayload> payloadClass() {
        return StatusChangedPayload.class;
    }

    @Override
    public AbstractMessage<StatusChangedPayload> produce(long dealId, long seq) {
        PaymentStatus[] statuses = PaymentStatus.values();
        var status = statuses[random.nextInt(statuses.length)];

        return new StatusChangedMessage(
                LocalDateTime.now(),
                dealId, seq, new StatusChangedPayload(status)
        );
    }
}
