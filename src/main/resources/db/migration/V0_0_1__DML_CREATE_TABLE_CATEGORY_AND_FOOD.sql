create table category (
    id serial primary key,
    name varchar(20) not null
);

create table food (
    id serial primary key,
    name varchar(20) not null,
    description varchar(100),
    value numeric(3,2) not null,
    category_id integer not null,

    constraint fk_category foreign key (category_id) references category(id)
);