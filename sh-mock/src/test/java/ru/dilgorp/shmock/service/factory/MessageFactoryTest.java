package ru.dilgorp.shmock.service.factory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.shmock.base.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MessageFactoryTest extends BaseTest {

    @Autowired
    private MessageFactory factory;

    @Test
    public void produce_happyPath() {
        int partitionSize = 10;
        long dealId = 1;
        long seq = 1;

        var result = factory.produce(partitionSize, dealId, seq);

        assertEquals(partitionSize, result.size());
        for (AbstractMessage<?> message : result) {
            assertEquals(dealId, message.getDealId());
            assertEquals(seq, message.getSeq());
            assertNotNull(message.getCreatedAt());
            assertNotNull(message.getPayload());
        }
    }
}