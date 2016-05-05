# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table at_inspection_configuration (
  id                            bigserial not null,
  inspection_name               varchar(255),
  inspection_class              varchar(255),
  constraint pk_at_inspection_configuration primary key (id)
);

create table at_inspection_configuration_attribute (
  id                            bigserial not null,
  inspection_configuration_id   bigint not null,
  attribute_name                varchar(255),
  index                         integer,
  data_type                     varchar(255),
  list_type                     varchar(255),
  constraint pk_at_inspection_configuration_attribute primary key (id)
);

alter table at_inspection_configuration_attribute add constraint fk_at_inspection_configuration_attribute_inspection_confi_1 foreign key (inspection_configuration_id) references at_inspection_configuration (id) on delete restrict on update restrict;
create index ix_at_inspection_configuration_attribute_inspection_confi_1 on at_inspection_configuration_attribute (inspection_configuration_id);


# --- !Downs

alter table if exists at_inspection_configuration_attribute drop constraint if exists fk_at_inspection_configuration_attribute_inspection_confi_1;
drop index if exists ix_at_inspection_configuration_attribute_inspection_confi_1;

drop table if exists at_inspection_configuration cascade;

drop table if exists at_inspection_configuration_attribute cascade;

