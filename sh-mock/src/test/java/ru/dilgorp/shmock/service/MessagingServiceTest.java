package ru.dilgorp.shmock.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.domain.message.RecalculatePriceMessage;
import ru.dilgorp.domain.message.StatusChangedMessage;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;
import ru.dilgorp.domain.message.payload.StatusChangedPayload;
import ru.dilgorp.shmock.base.BaseTest;
import ru.dilgorp.shmock.service.factory.MessageFactory;
import ru.dilgorp.shmock.stream.Sender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessagingServiceTest extends BaseTest {

    @Autowired
    private MessagingService service;

    @MockBean
    private MessageFactory messageFactory;

    @MockBean
    private Sender sender;

    @Captor
    private ArgumentCaptor<List<AbstractMessage<?>>> captor;

    @Test
    public void send_happyPath() {
        var partitionSize = 2;
        var dealsCount = 3;
        var seq = 1L;
        var partitionsShuffle = 2;
        var now = LocalDateTime.now();

        var orderedMessages = new ArrayList<AbstractMessage<?>>(dealsCount * partitionSize);
        for (int i = 0; i < dealsCount; i++) {
            List<AbstractMessage<?>> producedMessages = List.of(
                    new StatusChangedMessage(now, i, seq, new StatusChangedPayload(PaymentStatus.NOT_PAID)),
                    new RecalculatePriceMessage(now, i, seq, new RecalculatePricePayload(List.of(Service.DKP)))
            );
            when(messageFactory.produce(partitionSize, i, seq))
                    .thenReturn(producedMessages);

            orderedMessages.addAll(producedMessages);
        }

        service.send(partitionSize, dealsCount, seq, partitionsShuffle);

        for (int i = 0; i < dealsCount; i++) {
            verify(messageFactory).produce(partitionSize, i, seq);
        }

        verify(sender).send(captor.capture());

        var messages = captor.getValue();
        assertEquals(6, messages.size());

        for (AbstractMessage<?> message : messages) {
            assertEquals(seq, message.getSeq());
            assertTrue(message.getDealId() >= 0 && message.getDealId() < dealsCount);
            assertEquals(now, message.getCreatedAt());
            assertNotNull(message.getPayload());
        }

        assertEquals(orderedMessages.size(), messages.size());
        assertNotEquals(orderedMessages, messages);
    }
}