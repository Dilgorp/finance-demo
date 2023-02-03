package ru.dilgorp.finance.rabbit.service.connector;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.finance.rabbit.client.ShMockClientApi;

@Service
@RequiredArgsConstructor
public class ShMockConnectorService {
    private final ShMockClientApi shMockClientApi;

    public Long longTime(){
        return shMockClientApi.longTime();
    }
}
