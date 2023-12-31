create table publication (
    id bigint not null auto_increment,
     content text(65535),
      filename varchar(255),
       title varchar(255),
        user_id bigint,
         primary key (id)
                         ) engine=InnoDB;

create table user_role (
    user_id bigint not null,
     roles varchar(255)
                       ) engine=InnoDB;

create table users (
    id bigint not null auto_increment,
     activation_code varchar(255),
      active bit not null,
       email varchar(255),
        password varchar(255),
         username varchar(255),
          primary key (id)
                   ) engine=InnoDB;

alter table publication
    add constraint fk_publication_to_user
        foreign key (user_id) references users (id);

alter table user_role
    add constraint fk_user_role_to_user
        foreign key (user_id) references users (id);
