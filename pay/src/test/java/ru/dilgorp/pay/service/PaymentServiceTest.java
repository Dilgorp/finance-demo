package ru.dilgorp.pay.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.pay.entity.PaymentEntity;
import ru.dilgorp.pay.repository.PaymentRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    public void getPayment_happyPath(){
        var entity = new PaymentEntity(null, UUID.randomUUID(), PayPaymentStatus.PAID);
        var model = entity.toModel();

        when(paymentRepository.findByExternalId(entity.getExternalId()))
                .thenReturn(entity);

        var result = paymentService.getPayment(entity.getExternalId());

        assertEquals(model, result);
        verify(paymentRepository).findByExternalId(entity.getExternalId());
    }

    @Test
    public void newPayment_happyPath(){
        var externalId = UUID.randomUUID();
        var entity = new PaymentEntity(null, externalId, PayPaymentStatus.PAID);

        when(paymentRepository.findByExternalId(externalId)).thenReturn(entity);
        var result = paymentService.newPayment(externalId);

        assertEquals(entity.toModel(), result);
        verify(paymentRepository).findByExternalId(externalId);
        verify(paymentRepository, times(0)).save(any());
    }

    @Test
    public void newPayment_createNewPayment(){
        var externalId = UUID.randomUUID();
        var entity = new PaymentEntity(null, externalId, PayPaymentStatus.PROCESSING);

        when(paymentRepository.findByExternalId(externalId)).thenReturn(null);
        when(paymentRepository.save(entity)).thenReturn(entity);
        var result = paymentService.newPayment(externalId);

        assertEquals(entity.toModel(), result);
        verify(paymentRepository).findByExternalId(externalId);
        verify(paymentRepository).save(entity);
    }
}