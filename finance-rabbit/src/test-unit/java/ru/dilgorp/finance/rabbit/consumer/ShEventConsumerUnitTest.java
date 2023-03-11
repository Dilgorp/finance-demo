package ru.dilgorp.finance.rabbit.consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.message.RecalculatePriceMessage;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;
import ru.dilgorp.finance.rabbit.interceptor.LogMessageInterceptor;
import ru.dilgorp.finance.rabbit.service.PaymentService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShEventConsumerUnitTest {

    private ShEventConsumer shEventConsumer;

    @Mock
    private LogMessageInterceptor logMessageInterceptor;

    @Mock
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        shEventConsumer = new ShEventConsumer(List.of(logMessageInterceptor), paymentService);
    }

    @Test
    public void accept_happyPath() {
        var message = new RecalculatePriceMessage(
                LocalDateTime.now(),
                1, 1, new RecalculatePricePayload(List.of(Service.SBR))
        );

        shEventConsumer.accept(message);

        verify(logMessageInterceptor).intercept(message);
        verify(paymentService).processMessage(message);
    }
}