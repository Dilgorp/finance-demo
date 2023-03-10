package ru.dilgorp.finance.rabbit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "sh-mock-client",
        url = "${feign.sh-mock-client.base-url}"
)
public interface ShMockClientApi {
    @GetMapping(path = {"/long_time"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    Long longTime();
}
