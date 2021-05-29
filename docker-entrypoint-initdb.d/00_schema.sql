create table if not exists roles
(
    id   bigserial primary key,
    name text not null unique
);

create table if not exists users
(
    id       bigserial primary key,
    username text   not null unique,
    password text   not null,
    secret   text   not null,
    status   text   not null
);

create table if not exists user_roles
(
    user_id bigserial references users,
    role_id bigserial references roles,
    primary key (user_id, role_id)
);

create table if not exists tokens
(
    user_id bigserial references users,
    token text primary key
);

create table if not exists warehouses
(
    id   bigserial primary key,
    name text not null unique
);

create table if not exists products
(
    id                  bigserial primary key,
    vendor_code         integer not null unique,
    name                text    not null unique,
    last_purchase_price integer default 0,
    last_sale_price     integer default 0
);

create table if not exists m2o_products_warehouses
(
    warehouse_id bigserial not null references warehouses,
    product_id   bigserial not null references products,
    count        integer   not null default 0,
    primary key (warehouse_id, product_id)
);

create table if not exists sales
(
    id           bigserial primary key,
    warehouse_id bigserial references warehouses,
    date         timestamp not null default current_timestamp
);

create table if not exists purchases
(
    id           bigserial primary key,
    warehouse_id bigserial references warehouses,
    date         timestamp not null default current_timestamp
);

create table if not exists movings
(
    id                bigserial primary key,
    warehouse_from_id bigserial references warehouses,
    warehouse_to_id   bigserial references warehouses,
    date              timestamp not null default current_timestamp
);

create table if not exists o2m_sale_products
(
    sale_id       bigserial not null,
    product_id    bigserial not null,
    count         integer   not null,
    price_per_one integer   not null,
    primary key (sale_id, product_id)
);

create table if not exists o2m_purchase_products
(
    purchase_id   bigserial not null,
    product_id    bigserial not null,
    count         integer   not null,
    price_per_one integer   not null,
    primary key (purchase_id, product_id)
);

create table if not exists o2m_moving_products
(
    moving_id  bigserial not null,
    product_id bigserial not null,
    count      integer   not null,
    primary key (moving_id, product_id)
);

