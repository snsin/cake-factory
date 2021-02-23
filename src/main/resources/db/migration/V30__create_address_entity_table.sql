CREATE TABLE address_entity (
    id serial not null primary key,
    address_line1 varchar,
    address_line2 varchar,
    postcode varchar,
    user_id varchar
)
