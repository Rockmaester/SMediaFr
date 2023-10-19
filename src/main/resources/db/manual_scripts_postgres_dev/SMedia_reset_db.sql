drop database socialmedia_db;
create database socialmedia_db;

use smedia_db;

create table publication (
                             id bigint not null generated always as identity,
                             content varchar(65535),
                             filename varchar(255),
                             title varchar(255),
                             user_id bigint,
                             primary key (id)
);

create table user_role (
                           user_id bigint not null,
                           roles varchar(255)
);

create table users (
                       id bigint not null generated always as identity,
                       activation_code varchar(255),
                       active bool not null,
                       email varchar(255),
                       password varchar(255),
                       username varchar(255),
                       primary key (id)
);

alter table publication
    add constraint fk_publication_to_user
        foreign key (user_id) references users (id);

alter table user_role
    add constraint fk_user_role_to_user
        foreign key (user_id) references users (id);




insert into users (id, username, password, active)
values (1, 'admin', '123', true);

insert into user_role (user_id, roles)
values (1, 'USER'), (1, 'ADMIN');


insert into users (id, username, password, active)
values (2, 'user1', '1', true), (3, 'user2', '2', true), (4, 'user3', '3', true), (5, 'user4', '4', true);

insert into user_role (user_id, roles)
values (2, 'USER'), (3, 'USER'), (4, 'USER'), (5, 'USER');



create table user_subscriptions(
                                   channel_id bigint not null references users,
                                   subscriber_id bigint not null references users,
                                   primary key (channel_id, subscriber_id)
);



update users set password = MD5(password);
