
create table if not exists users
(
    id integer generated by default as identity primary key,
    username varchar not null unique,
    password varchar not null,
    email varchar not null unique,
    date_of_registration date,
    phone_number varchar(25),
    enabled bool not null default false
);

create table if not exists spare_part_names
(
    id integer generated by default as identity primary key,
    name varchar(50) unique
);


create table if not exists spare_parts
(
    id integer generated by default as identity primary key,
    spare_part_name_id integer references spare_part_names(id),
    number varchar(50) not null
);

create table if not exists posts
(
    id integer generated by default as identity primary key,
    title varchar(255) not null,
    user_id integer not null,
    spare_part_id integer not null references spare_parts(id)
);


create table if not exists car_brands
(
    id integer generated by default as identity primary key,
    brand_name varchar(30) not null
);

create table if not exists car_models
(
    id integer generated by default as identity primary key,
    car_brand_id integer not null references car_brands(id),
    model_name varchar(25)
);


create table if not exists car_model_spare_part
(
    spare_part_id integer references spare_parts(id),
    car_model_id integer references car_models(id)
);


create table if not exists car_model_generations
(
    id integer generated by default as identity primary key,
    car_model_id integer not null references car_models(id),
    start_year_of_prod date not null,
    last_year_of_prod date not null,
    generation_name varchar(25)
);

create table if not exists users_roles(
    user_id bigint references users(id) not null,
    role varchar(255) not null,
    primary key (user_id, role),
    constraint fk_users_roles_users foreign key (user_id) references users(id) on delete cascade on update no action
);


create table if not exists posts_images(
    post_id int not null references posts(id) on delete cascade,
    image varchar(255) not null
);
