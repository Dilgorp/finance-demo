package ru.dilgorp.finance.rabbit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dilgorp.domain.dto.PayPaymentDto;

import java.util.UUID;

@FeignClient(
        name = "pay-client",
        url = "${feign.pay-client.base-url}"
)
public interface PayClientApi {
    @GetMapping(path = {"/payments/external_id/{external_id}"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    PayPaymentDto getPayment(@PathVariable("external_id") UUID externalId);

    @PostMapping(path = {"/payments/external_id/{external_id}"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    PayPaymentDto postPayment(@PathVariable("external_id") UUID externalId);
}
