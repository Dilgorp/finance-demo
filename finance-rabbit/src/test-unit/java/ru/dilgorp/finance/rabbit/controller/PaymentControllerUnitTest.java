package ru.dilgorp.finance.rabbit.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dilgorp.domain.dto.PayPaymentDto;
import ru.dilgorp.domain.dto.PaymentDto;
import ru.dilgorp.domain.dto.PaymentWithPayDto;
import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.model.Payment;
import ru.dilgorp.finance.rabbit.service.PaymentService;
import ru.dilgorp.finance.rabbit.service.connector.PayConnectorService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentControllerUnitTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @Mock
    private PayConnectorService payConnectorService;

    @Test
    public void getByDealWithPay_happyPath() {
        var dealId = 1L;
        var services = List.of(Service.DKP, Service.SBR);
        var payments = List.of(
                new Payment(1L, dealId, 1L, Service.DKP, PaymentStatus.PAID,
                        LocalDateTime.now(), UUID.randomUUID()),
                new Payment(2L, dealId, 1L, Service.ONREG, PaymentStatus.PAID,
                        LocalDateTime.now(), UUID.randomUUID()),
                new Payment(3L, dealId, 1L, Service.SBR, PaymentStatus.PAID,
                        LocalDateTime.now(), UUID.randomUUID())
        );

        var payPaymentsDto = Map.of(
                payments.get(0).externalId(),
                new PayPaymentDto(10L, payments.get(0).externalId(), PayPaymentStatus.PROCESSING),

                payments.get(2).externalId(),
                new PayPaymentDto(11L, payments.get(1).externalId(), PayPaymentStatus.PAID)
        );

        var withPay = payments.stream()
                .filter(p -> services.contains(p.service()))
                .map(p -> new PaymentWithPayDto(PaymentDto.from(p), payPaymentsDto.get(p.externalId())))
                .toList();

        when(paymentService.getPayments(dealId)).thenReturn(payments);
        when(payConnectorService.getPayment(payments.get(0).externalId()))
                .thenReturn(payPaymentsDto.get(payments.get(0).externalId()));
        when(payConnectorService.getPayment(payments.get(2).externalId()))
                .thenReturn(payPaymentsDto.get(payments.get(2).externalId()));

        var result = paymentController.getByDealWithPay(dealId, services);

        assertEquals(withPay, result);
        verify(paymentService).getPayments(dealId);
        payPaymentsDto.forEach((uuid, paymentDto) ->
                verify(payConnectorService).getPayment(uuid));

    }

    @Test
    public void getByDealWithPay_servicesIsEmpty() {
        var dealId = 1L;
        var payments = List.of(
                new Payment(1L, dealId, 1L, Service.DKP, PaymentStatus.PAID,
                        LocalDateTime.now(), UUID.randomUUID()),
                new Payment(2L, dealId, 1L, Service.ONREG, PaymentStatus.PAID,
                        LocalDateTime.now(), UUID.randomUUID()),
                new Payment(3L, dealId, 1L, Service.SBR, PaymentStatus.PAID,
                        LocalDateTime.now(), UUID.randomUUID())
        );

        var payPaymentsDto = Map.of(
                payments.get(0).externalId(),
                new PayPaymentDto(10L, payments.get(0).externalId(), PayPaymentStatus.PROCESSING),

                payments.get(1).externalId(),
                new PayPaymentDto(11L, payments.get(1).externalId(), PayPaymentStatus.CANCELED),

                payments.get(2).externalId(),
                new PayPaymentDto(12L, payments.get(2).externalId(), PayPaymentStatus.PAID)
        );

        var withPay = payments.stream()
                .map(p -> new PaymentWithPayDto(PaymentDto.from(p), payPaymentsDto.get(p.externalId())))
                .toList();

        when(paymentService.getPayments(dealId)).thenReturn(payments);
        payments.forEach(p -> when(payConnectorService.getPayment(p.externalId()))
                .thenReturn(payPaymentsDto.get(p.externalId())));

        var result = paymentController.getByDealWithPay(dealId, List.of());

        assertEquals(withPay, result);
        verify(paymentService).getPayments(dealId);
        payPaymentsDto.forEach((uuid, paymentDto) ->
                verify(payConnectorService).getPayment(uuid));

    }
}