package ru.dilgorp.finance.rabbit.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.dilgorp.domain.dto.PayPaymentDto;
import ru.dilgorp.domain.dto.PaymentDto;
import ru.dilgorp.domain.dto.PaymentWithPayDto;
import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.model.Payment;
import ru.dilgorp.finance.rabbit.base.BaseControllerTest;
import ru.dilgorp.finance.rabbit.service.PaymentService;
import ru.dilgorp.finance.rabbit.service.connector.PayConnectorService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentControllerTest extends BaseControllerTest {

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private PayConnectorService payConnectorService;

    @Test
    @SneakyThrows
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
        payments.forEach(p -> when(payConnectorService.getPayment(p.externalId()))
                .thenReturn(payPaymentsDto.get(p.externalId())));

        var result = mockMvc.perform(get("/payments/by_deal/1/with_pay?services=DKP&services=SBR")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var resultList = objectMapper.readValue(result,
                objectMapper.getTypeFactory().constructCollectionType(List.class, PaymentWithPayDto.class));

        assertEquals(withPay, resultList);
        verify(paymentService).getPayments(dealId);
        verify(payConnectorService, times(2)).getPayment(any());
    }
}