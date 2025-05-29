create table roles
(
    id        bigserial primary key,
    role_name varchar(50)
    );


create table users
(
    id       bigserial primary key,
    login    varchar(50),
    password varchar(255)
    );

CREATE INDEX users_lower_login ON users(lower(login));


create table user_role
(
    user_id bigint not null constraint user_id_constraint references users,
    role_id bigint not null constraint role_id_constraint references roles,
    primary key (user_id, role_id)
    );