create table meeting_link(
                             id bigint primary key,
                             user_id bigint not null,
                             start_valid timestamp without time zone not null,
                             end_valid timestamp without time zone not null,
                             start_range timestamp without time zone not null,
                             end_range timestamp without time zone not null
);

grant select, insert, update, delete on meeting_link to meeter_app;

create sequence seq_meeting_link start with 1000;

grant select, update on seq_meeting_link to meeter_app;