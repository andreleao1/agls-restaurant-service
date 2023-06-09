create table restaurant_order(
     id varchar not null primary key,
     created_at timestamp,
     updated_at timestamp,
     payment_method varchar,
     total varchar,
     paid_value varchar,
     change varchar,
     is_paid boolean not null default false
);

create table order_food(
    food_id integer not null,
    order_id varchar not null,

    constraint fk_food foreign key (food_id) references food(id),
    constraint fk_order foreign key (order_id) references restaurant_order(id)
);