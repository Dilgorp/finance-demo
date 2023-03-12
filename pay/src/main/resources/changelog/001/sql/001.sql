create table payment
(
    id          BIGSERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    status      VARCHAR(20) NOT NULL
);