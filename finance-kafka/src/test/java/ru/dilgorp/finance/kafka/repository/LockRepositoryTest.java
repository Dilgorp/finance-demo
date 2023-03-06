package ru.dilgorp.finance.kafka.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dilgorp.finance.kafka.base.BaseRepositoryTest;
import ru.dilgorp.finance.kafka.entity.LockEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LockRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private LockRepository lockRepository;

    @Test
    public void findByDealIdAndSeq_happyPath(){
        var lockEntity = new LockEntity();
        lockEntity.setDealId(1L);
        lockEntity.setSeq(1L);

        lockRepository.save(lockEntity);

        var result = lockRepository.findByDealIdAndSeq(lockEntity.getDealId(), lockEntity.getSeq());

        lockEntity.setId(result.getId());
        assertEquals(lockEntity, result);
    }

    @Test
    public void insertInSeparateTransaction_happyPath(){
        long dealId = 1L;
        long seq = 1L;

        lockRepository.insertInSeparateTransaction(dealId, seq);

        var result = lockRepository.findAll().get(0);

        assertEquals(dealId, result.getDealId());
        assertEquals(seq, result.getSeq());
    }

    @Test
    public void lock_happyPath(){
        long dealId = 1L;
        long seq = 1L;

        var lockEntity = new LockEntity();
        lockEntity.setDealId(dealId);
        lockEntity.setSeq(seq);

        lockRepository.save(lockEntity);

        var result = lockRepository.lock(dealId, seq);
        assertEquals(dealId, result.getDealId());
        assertEquals(seq, result.getSeq());
    }
}