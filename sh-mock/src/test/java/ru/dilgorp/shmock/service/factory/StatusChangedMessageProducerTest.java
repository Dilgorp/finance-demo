package ru.dilgorp.shmock.service.factory;

import org.junit.jupiter.api.Test;
import ru.dilgorp.domain.message.StatusChangedMessage;
import ru.dilgorp.domain.message.payload.StatusChangedPayload;

import static org.junit.jupiter.api.Assertions.*;

class StatusChangedMessageProducerTest {

    private final StatusChangedMessageProducer producer = new StatusChangedMessageProducer();

    @Test
    public void payloadClass_happyPath(){
        var result = producer.payloadClass();
        assertEquals(StatusChangedPayload.class, result);
    }

    @Test
    public void produce_happyPath(){
        var dealId = 1L;
        var seq = 1L;

        var result = producer.produce(dealId, seq);
        assertEquals(StatusChangedMessage.class, result.getClass());
        assertEquals(dealId, result.getDealId());
        assertEquals(seq, result.getSeq());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getPayload());
        assertNotNull(result.getPayload().status());
    }
}