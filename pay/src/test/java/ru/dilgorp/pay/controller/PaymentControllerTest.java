package ru.dilgorp.pay.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.dilgorp.domain.dto.PayPaymentDto;
import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.domain.model.PayPayment;
import ru.dilgorp.pay.base.BaseControllerTest;
import ru.dilgorp.pay.service.PaymentService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentControllerTest extends BaseControllerTest {
    @MockBean
    private PaymentService paymentService;

    @Test
    public void getByExternalId_happyPath() throws Exception {
        var externalId = UUID.randomUUID();
        var payment = new PayPayment(1L, externalId, PayPaymentStatus.PAID);
        var dto = PayPaymentDto.from(payment);

        when(paymentService.getPayment(externalId)).thenReturn(payment);

        var result = mockMvc.perform(get("/payments/external_id/" + externalId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        assertEquals(dto, objectMapper.readValue(result, PayPaymentDto.class));
        verify(paymentService).getPayment(externalId);
    }

}