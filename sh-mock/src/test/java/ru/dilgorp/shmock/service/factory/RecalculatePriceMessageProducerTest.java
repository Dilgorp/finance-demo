package ru.dilgorp.shmock.service.factory;

import org.junit.jupiter.api.Test;
import ru.dilgorp.domain.message.RecalculatePriceMessage;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;

import static org.junit.jupiter.api.Assertions.*;

class RecalculatePriceMessageProducerTest {
    private final RecalculatePriceMessageProducer producer = new RecalculatePriceMessageProducer();

    @Test
    public void payloadClass_happyPath() {
        var result = producer.payloadClass();
        assertEquals(RecalculatePricePayload.class, result);
    }

    @Test
    public void produce_happyPath() {
        var dealId = 1L;
        var seq = 1L;

        var result = producer.produce(dealId, seq);
        assertEquals(RecalculatePriceMessage.class, result.getClass());
        assertEquals(dealId, result.getDealId());
        assertEquals(seq, result.getSeq());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getPayload());
        assertNotNull(result.getPayload().services());
        assertTrue(result.getPayload().services().size() > 0);
    }
}