package ru.dilgorp.finance.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.finance.kafka.repository.LockRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LockService {
    private final LockRepository lockRepository;

    private final Object lock = new Object();

    @Transactional
    public void tryLock(Long dealId, Long seq) {
        synchronized (lock) {
            try {
                var lock = lockRepository.findByDealIdAndSeq(dealId, seq);

                if (lock == null) {
                    lockRepository.insertInSeparateTransaction(dealId, seq);
                    log.info("Thread {} saves lock for locking of payment activities " +
                                    "(dealId={}, seq={})", Thread.currentThread().getId(),
                            dealId, seq);
                }
            } catch (Exception e) {
                log.info("Thread {} tried to initialize the lock for deal " +
                                "(dealId={}, seq={}): {}", Thread.currentThread().getId(),
                        dealId, seq, e);

                return;
            }

            lockRepository.lock(dealId, seq);
            log.info("Thread {} locks (dealId={}, seq={})", Thread.currentThread().getId(), dealId, seq);
        }
    }
}
