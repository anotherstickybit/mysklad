insert into roles (name)
values ('ROLE_ADMIN'),
       ('SKLAD_OPERATOR'),
       ('ROLE_USER');

insert into users (username, password, secret, status)
values ('admin', '$2a$04$r2TUGZNg9e1yqZDbJf2EJe/MjaYm3CzYiRRLii040sIx6frTV5FBy',
        '$2a$04$r2TUGZNg9e1yqZDbJf2EJe/MjaYm3CzYiRRLii040sIx6frTV5FBy', 'ACTIVE'),
       ('operator', '$2a$04$1MgQ2ocAZLcdyIjVRKFduevmnebYduiO91IZe0iCh4qVv1VbC6Gwy',
        '$2a$04$1MgQ2ocAZLcdyIjVRKFduevmnebYduiO91IZe0iCh4qVv1VbC6Gwy', 'ACTIVE'),
       ('user', '$2a$04$.GDXoe4W7C4B9V91fnyKH.SXa5UMaYp4EjhuX4vT60l0SCrP1W7vy',
        '$2a$04$.GDXoe4W7C4B9V91fnyKH.SXa5UMaYp4EjhuX4vT60l0SCrP1W7vy', 'ACTIVE')
;

insert into user_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 3)
;

insert into warehouses (name)
values ('SKLAD1'),
       ('SKLAD2')
;

insert into products(vendor_code, name)
values (12344, 'head and shoulders'),
       (12345, 'lego cars'),
       (12346, 'bread'),
       (12347, 'milk'),
       (12348, 'ice cream'),
       (12349, 'peper')
;

insert into m2o_products_warehouses (warehouse_id, product_id, count)
VALUES (1, 1, 10),
       (1, 2, 5),
       (2, 1, 30),
       (2, 3, 100),
       (2, 4, 60),
       (2, 5, 200),
       (2, 6, 240),
       (1, 3, 70),
       (1, 4, 150),
       (1, 5, 300),
       (1, 6, 260)
;