create table repeater(
                         id bigint primary key,
                         user_id bigint not null,
                         name varchar(255) not null,
                         weekdays varchar(255) not null,
                         start_time time not null,
                         end_time time not null
);

grant select, insert, update, delete on repeater to meeter_app;

create sequence seq_repeater start with 1000;

grant select, update on seq_repeater to meeter_app;