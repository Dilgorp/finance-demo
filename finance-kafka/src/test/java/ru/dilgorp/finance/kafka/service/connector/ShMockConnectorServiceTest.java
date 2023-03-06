package ru.dilgorp.finance.kafka.service.connector;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dilgorp.finance.kafka.client.ShMockClientApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShMockConnectorServiceTest {
    @Mock
    private ShMockClientApi shMockClientApi;

    @InjectMocks
    private ShMockConnectorService shMockConnectorService;

    @Test
    public void longTime_happyPath() {
        var longTime = 1000L;
        when(shMockClientApi.longTime()).thenReturn(longTime);

        var result = shMockConnectorService.longTime();

        assertEquals(longTime, result);
        verify(shMockClientApi).longTime();
    }

}