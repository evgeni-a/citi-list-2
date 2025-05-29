create table cities
(
    id    bigserial primary key,
    name  varchar(50),
    photo varchar(2048)
    );

CREATE INDEX cities_lower_name ON cities(lower(name));