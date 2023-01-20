CREATE TABLE drone
(
   id integer not null AUTO_INCREMENT,
   serial_number varchar (100),
   model varchar (20),
   weight_limit integer,
   battery_capacity integer,
   state varchar (20),
   primary key (id)
);

CREATE TABLE medication
(
   id integer not null AUTO_INCREMENT,
   name varchar (255),
   weight integer,
   code varchar (255),
   image varchar (255),
   drone_id integer,
   primary key (id)
);

create table checks
(
   id integer not null AUTO_INCREMENT,
   description varchar (255) not null,
   date DATETIME,
   primary key (id)
);

ALTER TABLE medication ADD CONSTRAINT medication_drone_fkey FOREIGN KEY (drone_id) REFERENCES drone;