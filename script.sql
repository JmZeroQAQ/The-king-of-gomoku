create table tkog.bot
(
    id          int auto_increment
        primary key,
    user_id     int            not null,
    title       varchar(100)   not null,
    description varchar(300)   null,
    content     varchar(10000) null,
    create_time datetime       null,
    modify_time datetime       null,
    constraint id
        unique (id)
);

create table tkog.record
(
    id            int auto_increment
        primary key,
    a_id          int           not null,
    b_id          int           not null,
    winner_id     int           not null,
    create_time   datetime      not null,
    history_steps varchar(1000) not null,
    win_set       varchar(1000) not null,
    constraint id
        unique (id)
);

create table tkog.user
(
    id       int auto_increment
        primary key,
    username varchar(100)     not null,
    password varchar(1000)    not null,
    avatar   varchar(1000)    not null,
    rating   int default 1500 null,
    constraint id
        unique (id),
    constraint username
        unique (username)
);

