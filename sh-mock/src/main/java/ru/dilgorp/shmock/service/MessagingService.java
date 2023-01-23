package ru.dilgorp.shmock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.shmock.service.factory.MessageFactory;
import ru.dilgorp.shmock.stream.Sender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessagingService {

    private final MessageFactory messageFactory;
    private final Sender sender;

    public void send(int partitionSize, int dealsCount, long seq, int partitionsShuffle) {

        var messages = new ArrayList<AbstractMessage<?>>(dealsCount * partitionSize);
        var messagesToShuffle = new ArrayList<AbstractMessage<?>>(partitionSize * partitionsShuffle);
        for (int i = 0; i < dealsCount; i++) {
            messagesToShuffle.addAll(messageFactory.produce(partitionSize, i, seq));

            if ((i > 0) && (i % partitionsShuffle == 0)) {
                fillMessages(messages, messagesToShuffle);
            }
        }

        if (messagesToShuffle.size() > 0) {
            fillMessages(messages, messagesToShuffle);
        }

        sender.send(messages);
    }

    private static void fillMessages(List<AbstractMessage<?>> messages, List<AbstractMessage<?>> messagesToShuffle) {
        Collections.shuffle(messagesToShuffle);
        messages.addAll(messagesToShuffle);
        messagesToShuffle.clear();
    }
}
