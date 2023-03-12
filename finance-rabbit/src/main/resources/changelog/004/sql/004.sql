CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

alter table payment add column external_id uuid;
update payment set external_id = uuid_generate_v4() where external_id is null;
alter table payment alter column external_id set not null;