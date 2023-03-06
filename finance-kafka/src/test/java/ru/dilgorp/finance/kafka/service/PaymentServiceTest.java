package ru.dilgorp.finance.kafka.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.message.RecalculatePriceMessage;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;
import ru.dilgorp.finance.kafka.entity.PaymentEntity;
import ru.dilgorp.finance.kafka.repository.PaymentRepository;
import ru.dilgorp.finance.kafka.service.connector.ShMockConnectorService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private LockService lockService;

    @Mock
    private ShMockConnectorService shMockConnectorService;

    @Mock
    private PaymentRepository paymentRepository;

    @Captor
    private ArgumentCaptor<PaymentEntity> captor;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void processMessage_happyPath() {
        var dealId = 1L;
        var seq = 2L;

        var message = new RecalculatePriceMessage(LocalDateTime.now(),
                dealId, seq, new RecalculatePricePayload(List.of(Service.SBR)));

        paymentService.processMessage(message);

        verify(lockService).tryLock(dealId, seq);
        verify(shMockConnectorService).longTime();
        verify(paymentRepository).save(captor.capture());

        var entity = captor.getValue();
        assertEquals(dealId, entity.getDealId());
        assertEquals(seq, entity.getSeq());
    }
}