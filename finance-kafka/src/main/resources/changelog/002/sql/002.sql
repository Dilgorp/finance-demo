create table payment
(
    id      BIGSERIAL PRIMARY KEY,
    deal_id BIGINT,
    seq     BIGINT,
    service VARCHAR(10),
    status  VARCHAR(10)
);