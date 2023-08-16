create table sec_user(
                         id bigint primary key,
                         first_name varchar(255),
                         last_name varchar(255),
                         email varchar(255),
                         password varchar(255)
);

grant select, insert, update, delete on sec_user to meeter_app

create sequence seq_sec_user start with 1000;

grant select, update on seq_sec_user to meeter_app;