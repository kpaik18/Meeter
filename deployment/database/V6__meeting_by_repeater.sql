alter table meeting
    add column repeater_id bigint;

alter table meeting
    add constraint fk_meeting_repeater_id
        foreign key (repeater_id)
            references repeater(id);
