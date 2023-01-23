package ru.dilgorp.shmock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.shmock.service.MessagingService;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessagingController {

    private final MessagingService messagingService;

    @GetMapping("/send")
    public void send(
            @RequestParam(value = "partitionSize", required = false, defaultValue = "10")
            int partitionSize,
            @RequestParam(value = "dealsCount", required = false, defaultValue = "100")
            int dealsCount,
            @RequestParam(value = "seq")
            long seq,
            @RequestParam(value = "partitionsShuffle", required = false, defaultValue = "10")
            int partitionsShuffle
    ){
        messagingService.send(partitionSize, dealsCount, seq, partitionsShuffle);
    }
}
