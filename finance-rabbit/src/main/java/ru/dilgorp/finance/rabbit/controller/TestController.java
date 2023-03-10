package ru.dilgorp.finance.rabbit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.finance.rabbit.service.connector.ShMockConnectorService;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final ShMockConnectorService shMockConnectorService;

    @GetMapping
    public Long longTime() {
        return shMockConnectorService.longTime();
    }
}
