insert into users(username, password, email, date_of_registration, phone_number)
VALUES ('roma3',
        '$2a$12$NWOOOaosDf91qi53E/WxceZNxZ8V9BnpnDfMEZuoiL1hXVyyuRnfi',
        'roma3@gmail.com',
        date(now()),
        '+375447517324'),
       ('roma4', '$2a$12$4LtpASJPk0yXfmt35URB1uqMnmxjhzDW7dUP6de2mC.QKl3H5pc82',
        'roma4@gmail.com', date(now()), '+375291234567');


insert into car_brands(brand_name) values
                                       ('BMW'), ('Opel'), ('Mazda');

insert into car_models(car_brand_id, model_name) VALUES
                                                     (1, '7 серия'), (1, '4 серия'), (2, 'Astra'), (3, '3 mps');

insert into users_roles (user_id, role)
values (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER');