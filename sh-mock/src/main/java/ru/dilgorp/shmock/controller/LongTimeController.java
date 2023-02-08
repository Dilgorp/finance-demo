package ru.dilgorp.shmock.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LongTimeController {

    @GetMapping("/long_time")
    @SneakyThrows
    public int getLongTime() {
        int millis = 1000;
        Thread.sleep(millis);
        return millis;
    }
}
