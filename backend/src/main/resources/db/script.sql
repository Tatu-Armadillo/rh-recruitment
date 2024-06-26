-- do $$ 
-- begin
--   if not exists (
--         select 1
--         from information_schema.schemata
--         where schema_name = 'challenge'
--     ) then
        drop schema if exists challenge cascade; -- REMOVE
        create schema challenge;

        create table permission (
            id_permission bigserial primary key,
            description varchar(255) unique
        );

        create table users(
            id_user bigserial primary key,
            user_name varchar(255) unique not null,
            full_name varchar(255) not null,
            password varchar(255) not null,
            account_non_expired boolean not null,
            account_non_locked boolean not null,
            credentials_non_expired boolean not null,
            enabled boolean not null
        );

        create table user_permission(
            id_user bigint,
            id_permission bigint,
            primary key ( id_user, id_permission)
        );

        create table employee (
            id_employee bigserial primary key,
            name varchar(200) not null,
            cpf varchar(11) unique not null,
            phone varchar(13) unique not null,
            email varchar(200) unique not null,
            remuneration NUMERIC(7,2),
            start_date timestamp,
            current_position bigint,
            users bigint
        );

        create table job (
            id_job bigserial primary key,
            title varchar(200) not null,
            description varchar(500) not null,
            requirements varchar(500) not null
        );

        create table request_job( 
            id_request_job bigserial primary key,
            quantity_opportunities bigint,
            create_date timestamp,
            closed boolean,
            closed_date timestamp,
            responsible bigint,
            job bigint
        );

        create table apply(
            id_apply bigserial primary key,
            status varchar(20) not null,
            reason varchar(200),
            applyed_date timestamp,
            employee_request bigint,
            request_job bigint
        );

    alter table user_permission add constraint fk_user_permission_user foreign key (id_user) references users (id_user);
    alter table user_permission add constraint fk_user_permission_permission foreign key (id_permission) references permission (id_permission);
    alter table apply add constraint fk_apply_employee foreign key (employee_request) references employee (id_employee);
    alter table apply add constraint fk_apply_request_job foreign key (request_job) references request_job (id_request_job);
    alter table employee add constraint fk_employee_user foreign key (users) references users (id_user);
    alter table employee add constraint fk_employee_job foreign key (current_position) references job (id_job);
    alter table request_job add constraint fk_request_job_employee foreign key (responsible) references employee (id_employee);
    alter table request_job add constraint fk_request_job_job foreign key (job) references job (id_job);

    
    insert into challenge.permission (description) values ('ADMINISTRATOR'), ('RESPONSIBLE_RECRUITER'), ('EMPLOYEE');
    -- password 123456
    INSERT INTO challenge.users (user_name,full_name,"password",account_non_expired,account_non_locked,credentials_non_expired,enabled) VALUES ('adm','adm','$2a$10$PqsrFKSSRev9lL0BMAE.IOvDB4r6plBA7c45UDzz4v0Wu1Es9XMs.',true,true,true,true);
    INSERT INTO challenge.user_permission (id_user,id_permission) VALUES (1,2), (1,3), (1,1);
    INSERT INTO challenge.employee ("name",cpf,phone,email,remuneration,start_date,current_position,users) VALUES ('employee adm','12345678901','40028922','employee.adm@teste.com',NULL,'2024-06-24 12:39:00.685236',NULL,1);

--     end if;
-- end $$;