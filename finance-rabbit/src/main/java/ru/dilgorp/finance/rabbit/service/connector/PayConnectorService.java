package ru.dilgorp.finance.rabbit.service.connector;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.domain.dto.PayPaymentDto;
import ru.dilgorp.finance.rabbit.client.PayClientApi;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayConnectorService {
    private final PayClientApi payClientApi;

    public PayPaymentDto getPayment(UUID externalId){
        return payClientApi.getPayment(externalId);
    }
}
