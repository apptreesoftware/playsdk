# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table note (
  id                            varchar(255) not null,
  work_order_id                 bigint not null,
  text                          varchar(255),
  created_by                    varchar(255),
  constraint pk_note primary key (id)
);

create table session (
  token                         varchar(255),
  username                      varchar(255),
  login_date                    timestamp
);

create table user (
  id                            bigint not null,
  username                      varchar(255),
  password                      varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  email                         varchar(255),
  phone                         varchar(255),
  employee_id                   varchar(255),
  supervisor_id                 bigint,
  constraint pk_user primary key (id)
);
create sequence user_seq;

create table work_order (
  id                            bigint not null,
  number                        varchar(255),
  description                   varchar(255),
  assigned_to                   varchar(255),
  requestor_id                  varchar(255),
  priority                      varchar(255),
  constraint pk_work_order primary key (id)
);
create sequence work_order_seq;

alter table note add constraint fk_note_work_order_id foreign key (work_order_id) references work_order (id) on delete restrict on update restrict;
create index ix_note_work_order_id on note (work_order_id);


# --- !Downs

alter table note drop constraint if exists fk_note_work_order_id;
drop index if exists ix_note_work_order_id;

drop table if exists note;

drop table if exists session;

drop table if exists user;
drop sequence if exists user_seq;

drop table if exists work_order;
drop sequence if exists work_order_seq;

