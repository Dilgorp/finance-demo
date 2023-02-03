package ru.dilgorp.finance.rabbit.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dilgorp.finance.rabbit.base.BaseTest;
import ru.dilgorp.finance.rabbit.entity.LockEntity;
import ru.dilgorp.finance.rabbit.repository.LockRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LockServiceTest extends BaseTest {
    @MockBean
    private LockRepository lockRepository;

    @Autowired
    private LockService lockService;

    @Test
    public void tryLock_happyPath() {
        var dealId = 1L;
        var seq = 100L;

        var lockEntity = new LockEntity(null, dealId, seq);

        when(lockRepository.findByDealIdAndSeq(dealId, seq)).thenReturn(lockEntity);

        lockService.tryLock(dealId, seq);

        verify(lockRepository).findByDealIdAndSeq(dealId, seq);
        verify(lockRepository, times(0)).insertInSeparateTransaction(any(), any());
        verify(lockRepository).lock(dealId, seq);
    }

    @Test
    public void tryLock_LockIsNull() {
        var dealId = 1L;
        var seq = 100L;

        when(lockRepository.findByDealIdAndSeq(dealId, seq)).thenReturn(null);

        lockService.tryLock(dealId, seq);

        verify(lockRepository).findByDealIdAndSeq(dealId, seq);
        verify(lockRepository).insertInSeparateTransaction(dealId, seq);
        verify(lockRepository).lock(dealId, seq);
    }

    @Test
    public void tryLock_InitializationThrowsException() {
        var dealId = 1L;
        var seq = 100L;

        when(lockRepository.findByDealIdAndSeq(dealId, seq)).thenThrow(IllegalArgumentException.class);

        lockService.tryLock(dealId, seq);

        verify(lockRepository).findByDealIdAndSeq(dealId, seq);
        verify(lockRepository, times(0)).insertInSeparateTransaction(any(), any());
        verify(lockRepository, times(0)).lock(any(), any());
    }
}