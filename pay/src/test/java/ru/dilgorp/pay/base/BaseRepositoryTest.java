package ru.dilgorp.pay.base;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Consumer;

public class BaseRepositoryTest extends BaseTest {

    @Autowired
    private TransactionTemplate transactionalTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected void transactional(Consumer<TransactionStatus> block) {
        transactionalTemplate.execute(status -> {
            block.accept(status);
            return status;
        });
    }

    @BeforeEach
    public void setUp() {
        transactional(status -> jdbcTemplate.execute("""
                DO $func$
                BEGIN
                EXECUTE  (SELECT 'TRUNCATE TABLE ' || string_agg(oid::regclass::text, ', ') || ' CASCADE'
                FROM pg_class
                WHERE relkind = 'r'
                AND relnamespace = 'public'::regnamespace
                AND relname != 'databasechangelog'
                AND relname != 'databasechangeloglock'
                );
                END
                $func$
                    
                """));
    }
}
