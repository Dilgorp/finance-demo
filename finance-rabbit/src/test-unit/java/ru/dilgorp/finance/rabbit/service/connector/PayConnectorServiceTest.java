package ru.dilgorp.finance.rabbit.service.connector;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dilgorp.domain.dto.PayPaymentDto;
import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.finance.rabbit.client.PayClientApi;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PayConnectorServiceTest {

    @InjectMocks
    private PayConnectorService payConnectorService;

    @Mock
    private PayClientApi payClientApi;

    @Test
    public void getPayment_happyPath(){
        var externalId = UUID.randomUUID();
        var dto = new PayPaymentDto(1L, externalId, PayPaymentStatus.PAID);

        when(payClientApi.getPayment(externalId)).thenReturn(dto);

        var result = payConnectorService.getPayment(externalId);

        assertEquals(dto, result);
        verify(payClientApi).getPayment(externalId);
    }

}