DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id           bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    username     varchar(32) UNIQUE  NOT NULL,
    "password"   varchar             NOT NULL,
    email        varchar(256) UNIQUE NOT NULL,
    "class"      varchar(20)         NOT NULL,
    profession   varchar(30)         NOT NULL,
    birthday     date,
    registration timestamp           NOT NULL,
    "role"       varchar             NOT NULL,
    banned       bool
);

CREATE TABLE products
(
    id         bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    "name"     varchar(256)                                   NOT NULL,
    price      decimal(10, 2)                                 NOT NULL,
    "class"    varchar(20)                                    NOT NULL,
    profession varchar(30)                                    NOT NULL,
    user_id    bigint REFERENCES users (id) ON DELETE CASCADE NOT NULL
);