--date: 2023-01-02
--author: stvort

drop table if exists reviews;
drop table if exists films_actors;
drop table if exists films;
drop table if exists directors;
drop table if exists actors;
drop table if exists genres;

create table directors (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table actors (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table films (
    id bigserial,
    genre_id bigint references genres (id),
    director_id bigint references directors (id),
    title varchar(50),
    description varchar(2000),
    primary key (id)
);

create table films_actors (
    film_id bigint not null references films (id) on delete cascade,
    actor_id bigint not null references actors (id) on delete cascade
);

create table reviews (
    id bigserial,
    film_id bigint references films (id) on delete cascade,
    review_text varchar(2000),
    primary key (id)
);