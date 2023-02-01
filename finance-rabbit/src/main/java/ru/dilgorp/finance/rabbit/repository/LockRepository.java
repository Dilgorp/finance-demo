package ru.dilgorp.finance.rabbit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dilgorp.finance.rabbit.entity.LockEntity;

public interface LockRepository extends JpaRepository<LockEntity, Long> {
}
