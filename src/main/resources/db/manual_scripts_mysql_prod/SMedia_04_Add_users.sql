use socialmedia_db;

insert into users (id, username, password, active)
values (2, 'user1', '1', true), (3, 'user2', '2', true), (4, 'user3', '3', true), (5, 'user4', '4', true);

insert into user_role (user_id, roles)
values (2, 'USER'), (3, 'USER'), (4, 'USER'), (5, 'USER');