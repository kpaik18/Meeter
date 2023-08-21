create table meeting(
                        id bigint primary key,
                        owner_id bigint not null,
                        participant_id bigint not null,
                        name varchar(255),
                        status varchar(255) not null,
                        start_time timestamp without time zone not null,
                        end_time timestamp without time zone not null
);

grant select, insert, update, delete on meeting to meeter_app;

create sequence seq_meeting start with 1000;

grant select, update on seq_meeting to meeter_app;