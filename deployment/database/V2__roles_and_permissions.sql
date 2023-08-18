create table permission(
                           id bigint primary key,
                           code varchar(255) not null,
                           name varchar(255) not null
);

create sequence seq_permission start with 1000;

grant select, insert, update, delete on permission to meeter_app;
grant select, update on seq_permission to meeter_app;


create table role(
                     id bigint primary key,
                     code varchar(255) not null,
                     name varchar(255) not null
);

create sequence seq_role start with 1000;

grant select, insert, update, delete on role to meeter_app;
grant select, update on seq_role to meeter_app;


create table role_permission(
                                role_id bigint not null,
                                permission_id bigint not null,
                                constraint fk_role_permission_role
                                    foreign key (role_id)
                                        references role(id),
                                constraint fk_role_permission_permission
                                    foreign key (permission_id)
                                        references permission(id),
                                constraint uk_role_permission
                                    unique(role_id, permission_id)
);

grant select, insert, update, delete on role_permission to meeter_app;

insert into permission (id, code, name)
values
    (1, 'ADMIN', 'ადმინისტრატორი'),
    (2, 'USER', 'მომხმარებელი');

insert into role (id, code, name)
values
    (1, 'ADMIN', 'ადმინისტრატორი'),
    (2, 'USER', 'მომხმარებელი');

insert into role_permission	(role_id, permission_id)
values
    (1, 1),
    (2, 2);