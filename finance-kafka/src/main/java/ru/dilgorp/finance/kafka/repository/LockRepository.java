package ru.dilgorp.finance.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.finance.kafka.entity.LockEntity;

public interface LockRepository extends JpaRepository<LockEntity, Long> {

    LockEntity findByDealIdAndSeq(Long dealId, Long seq);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query(nativeQuery = true, value = "insert into lock (deal_id, seq) values (:dealId, :seq)")
    void insertInSeparateTransaction(Long dealId, Long seq);

    @Query(nativeQuery = true, value = "select * from lock where deal_id=:dealId and seq=:seq for update")
    LockEntity lock(Long dealId, Long seq);
}
