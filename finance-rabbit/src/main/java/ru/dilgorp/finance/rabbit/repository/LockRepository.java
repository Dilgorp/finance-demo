package ru.dilgorp.finance.rabbit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.finance.rabbit.entity.LockEntity;

public interface LockRepository extends JpaRepository<LockEntity, Long> {

    public LockEntity findByDealIdAndSeq(Long dealId, Long seq);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query(nativeQuery = true, value = "insert into lock (deal_id, seq) values (:dealId, :seq)")
    public void insertInSeparateTransaction(Long dealId, Long seq);

    @Query(nativeQuery = true, value = "select * from lock where deal_id=:dealId and seq=:seq for update")
    public LockEntity lock(Long dealId, Long seq);
}
