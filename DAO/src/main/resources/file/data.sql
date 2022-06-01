BEGIN;

DROP TABLE if exists clients,qualifications, car_clients,label,employers,master_receivers,masters,orders,outfits,role_user,state_car_breakdown,state_orders,state_outfits,car_breakdowns CASCADE;

create table if not exists qualifications(
id UUID primary key,
name_qualifications varchar(50));

create table if not exists employers(
id UUID primary key ,
name varchar(50),
education varchar(50),
qualification_id UUID,
password varchar(50) not null unique,
login varchar(50) not null ,
home_address varchar(50),
phone varchar(50) not null,
mail varchar(50),
descriptions varchar(50) not null,
foreign key (qualification_id) references qualifications(id)
);

CREATE INDEX employers_index ON employers(password,login);

create table if not exists masters(
id UUID primary key,
foreign key (id) references employers(id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table if not exists master_receivers(
id UUID primary key,
foreign key (id) references employers(id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table if not exists role_user(
id UUID primary key,
role varchar(50) not null unique);

create table if not exists state_car_breakdown(
id UUID primary key,
state_car varchar(50) not null unique);

create table if not exists clients(
id UUID primary key,
password varchar(100) not null unique,
login varchar(100) not null,
first_name varchar(100),
role_user UUID not null,
last_name varchar(100),
email varchar(100),
phone varchar(100),
descriptions varchar(100) not null,
constraint foreign_key_role_user foreign key (role_user) references role_user(id)
);

CREATE INDEX clients_index ON clients(password,login);

create table if not exists car_clients(
id UUID primary key,
id_clients UUID ,
id_breakdown UUID,
summary varchar(250),
ear date,
run varchar(250),
metadata_car varchar(250),
descriptions varchar(250) not null,
constraint foreign_key_clients foreign key (id_clients) references clients(id)
);

create table if not exists car_breakdowns(
id UUID primary key,
descriptions varchar(250) not null,
name varchar(50) not null,
id_car UUID not null,
locationd varchar(50),
run_car_size varchar(50) not null,
state_id UUID not null,
constraint foreign_key_state_car_breakdown foreign key (state_id) references state_car_breakdown(id),
constraint foreign_key_state_car_breakdown_car_clients foreign key (id_car) references car_clients(id)
);


create table if not exists state_orders(
id UUID primary key,
state varchar(50)  not null unique);

create table if not exists orders(
id UUID primary key,
descriptions varchar(50) not null,
id_car UUID not null,
id_masters UUID,
created_date timestamp,
id_outfits UUID unique,
updated_date timestamp,
id_state_order UUID not null,
constraint foreign_key_master_orders foreign key (id_car) references car_clients(id),
constraint foreign_key_id_orders foreign key (id_masters) references master_receivers(id),
constraint foreign_state_order foreign key (id_state_order) references state_orders(id)
);
CREATE INDEX car_orders ON orders(updated_date,created_date);

create table if not exists label(
call_date timestamp,
name varchar(50),
id_clients UUID,
id_orders UUID,
primary key (id_clients,id_orders),
foreign key (id_clients) references clients(id),
foreign key (id_orders) references orders(id)
);

create table if not exists state_outfits(
id UUID primary key,
state varchar(50) not null unique);

create table if not exists outfits(
id UUID primary key,
name_outfits varchar(45),
descriptions varchar(100) ,
start_date timestamp,
id_master UUID,
id_orders  UUID,
end_date timestamp,
price_sum real,
state_outfits UUID ,
constraint foreign_key_masters_outfits foreign key (id_master) references masters(id),
constraint foreign_key_state_outfits foreign key (state_outfits) references state_outfits(id),
constraint foreign_key_orders_outfits foreign key (id_orders) references orders(id)
);

INSERT INTO public.role_user  (id,role)
VALUES
('c1a4d3ea-a5e5-3205-beb2-894e21c4a2bf', 'REGISTERED'),
('c65b6b5a-311f-369f-9936-5e0b45ca6907', 'UNREGISTERED');

INSERT INTO public.qualifications(id, name_qualifications)
VALUES ('cda01a34-4119-3e5e-9ab9-60b341f233fb', 'DISC_EDITING'),
('efa1d473-42dc-3d5c-af48-448b55c705cc', 'ELECTRICIAN');

INSERT INTO public.employers(
id, name, education, qualification_id, password, login, home_address, phone, mail, descriptions)
VALUES ('cda01a34-4119-3e5e-9ab9-60b341f233fb', 'test', 'test', 'cda01a34-4119-3e5e-9ab9-60b341f233fb',
 'test1', 'test1','test', 'test', 'test', 'test'),
('cda01a34-4119-3e5e-9ab9-60b341f234fb', 'test', 'test', 'cda01a34-4119-3e5e-9ab9-60b341f233fb', 'test',
 'test2','test2', 'test', 'test', 'test');

INSERT INTO public.master_receivers(id)
VALUES ('cda01a34-4119-3e5e-9ab9-60b341f234fb');

INSERT INTO public.masters(id)
VALUES ('cda01a34-4119-3e5e-9ab9-60b341f233fb');

INSERT INTO public.clients(
	id, password, login, first_name, role_user, last_name, email, phone, descriptions)
	VALUES ('8ef08b2c-7ffa-4373-ad3a-72ae64fd4935', 'clients', 'clients', 'clients',
	'c65b6b5a-311f-369f-9936-5e0b45ca6907',
	 'clients', 'clients', 'clients', 'clients');

INSERT INTO public.state_car_breakdown(
	id, state_car)
	VALUES ('acfd38b7-5861-37a2-b04d-ce420b134a80', 'CORRECTED'),
	('b3c1b7df-bc67-3b62-adb7-f44946c07784', 'NOT_FIXED');

INSERT INTO public.state_outfits(
	id, state)
	VALUES ('b1a326c0-6d88-3f04-af73-d70f50197905','END')
	,('9f20f1fb-4bd4-3381-84d4-f642b3159812','WORK')
	,('8eaf13f3-6ff5-3721-8a27-449e4a36517e', 'NO_STATE');

INSERT INTO public.state_orders(
	id, state)
	VALUES
	('9208fdeb-4912-3594-91a5-aa9c4a66aba1', 'TEMPLATE'),
	('e11c6364-ef1b-36b5-ac2b-5e507c7d0910', 'RECORDED'),
	('085778f9-9947-3e51-acc3-88465338089f', 'IN_WORK'),
	('36a8522f-d7b4-3401-b77d-a6df41b23baf', 'CAR_GIVEN'),
	('76fd9514-6e9a-3d90-9b90-798a17702b63', 'CAR_ACCEPTED'),
	('97f03df1-695a-3aed-a95f-e723bc3cf67c', 'WAIT_CLIENT'),
	('ad6c3588-0c58-397c-83d6-0a6ad0f23737', 'REQUEST'),
	('61613d5a-4896-3fbf-9bdb-8cb2f476b7f6', 'BID');

	INSERT INTO public.state_outfits(
	id, state)
	VALUES ('e11c6364-ef1b-36b5-ac2b-5e507c7d0910', 'RECORDED');

	INSERT INTO public.state_car_breakdown(
	id, state_car)
	VALUES
	( 'edf6e973-473e-390b-91da-a7601c0d434d','NEEDS_CORRECTED' ),
	('279ac5dd-73c3-3578-bebe-b10e172c4f38','IMPORTANT' );

COMMIT;