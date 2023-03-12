package ru.dilgorp.finance.rabbit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.message.RecalculatePriceMessage;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;
import ru.dilgorp.finance.rabbit.entity.PaymentEntity;
import ru.dilgorp.finance.rabbit.repository.PaymentRepository;
import ru.dilgorp.finance.rabbit.service.connector.ShMockConnectorService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void getPayments_happyPath() {
        var dealId = 1L;

        var entities = List.of(
                new PaymentEntity(1L, dealId, 1L, Service.DKP, PaymentStatus.PAID,
                        LocalDateTime.now(), UUID.randomUUID()),
                new PaymentEntity(2L, dealId, 1L, Service.DKP, PaymentStatus.PAID,
                        LocalDateTime.now(), UUID.randomUUID()),
                new PaymentEntity(3L, dealId, 1L, Service.DKP, PaymentStatus.PAID,
                        LocalDateTime.now(), UUID.randomUUID())
        );

        var models = entities.stream().map(PaymentEntity::toModel).toList();

        when(paymentRepository.findAllByDealId(dealId)).thenReturn(entities);

        var result = paymentService.getPayments(dealId);

        assertEquals(models, result);
        verify(paymentRepository).findAllByDealId(dealId);
    }
}