insert into bamper.users(username, password, email, date_of_registration, phone_number, enabled)
VALUES ('roma3',
        '$2a$12$NWOOOaosDf91qi53E/WxceZNxZ8V9BnpnDfMEZuoiL1hXVyyuRnfi',
        'roma3@gmail.com',
        date(now()),
        '+375447517324', true),
       ('roma4', '$2a$12$WI517GLLWbav.zFkrpgGheYbSfctLph8TLZAINfScsxRhoi5B5tg.',
        'roma4@gmail.com', date(now()), '+375291234567', true);


insert into bamper.car_brands(brand_name) values
                                       ('BMW'), ('Opel'), ('Mazda');

insert into bamper.car_models(car_brand_id, model_name) VALUES
                                                     (1, '7 серия'), (1, '4 серия'), (2, 'Astra'), (3, '3 mps');

insert into bamper.users_roles (user_id, role)
values (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER');

insert into bamper.spare_part_names(name) values ('Карданный вал'), ('Колесо'), ('Турбина'), ('Глушитель');

insert into bamper.spare_parts(spare_part_name_id, number) VALUES (1, '42133513'), (2, '435133131312'), (3, '423245323');

insert into bamper.posts(title, user_id, spare_part_id) VALUES ('ПОСТ1', 1, 1), ('ПОСТ2', 1, 2);

insert into bamper.user_reviews(id, stars, title, text, user_id, author_id) values (1, 5, 'Отличный продавец',  'Все гуд', 1, 2),
    (2, 3, 'Пойдет', 'Долгая доставка', 2, 1);