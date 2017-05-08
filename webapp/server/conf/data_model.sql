/*
sudo -u postgres pg_createcluster -p 43214 9.5 teacherapps
sudo -u postgres psql -p 43214 -c "CREATE USER teacherapp_admin PASSWORD 'aEr56yUI.,';"
sudo -u postgres psql -p 43214 -c "DROP DATABASE IF EXISTS teacherapps"
sudo -u postgres psql -p 43214 -c "CREATE DATABASE teacherapps WITH TEMPLATE = template0 ENCODING = 'UTF8';"
sudo -u postgres psql -p 43214 -c "ALTER DATABASE teacherapps OWNER TO teacherapp_admin;"
sudo -u postgres psql -p 43214 -d teacherapps -f webapp/server/conf/data_model.sql

To connect:
psql -h localhost -p 43214 -d teacherapps -U teacherapp_admin -W
*/

DROP TABLE IF EXISTS pupils CASCADE;
DROP TABLE IF EXISTS classes CASCADE;
DROP TABLE IF EXISTS teachers CASCADE;
DROP TYPE IF EXISTS class_levels;


CREATE TABLE teachers(
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    disabled BOOLEAN NOT NULL DEFAULT FALSE,
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now()
);


CREATE TABLE classes(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    teacher_id INT NOT NULL REFERENCES teachers ON DELETE CASCADE,
    disabled BOOLEAN NOT NULL DEFAULT FALSE,
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now()
);

CREATE TABLE pupils(
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    class_id INT NOT NULL REFERENCES classes ON DELETE CASCADE,
    disabled BOOLEAN NOT NULL DEFAULT FALSE,
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now()
);



CREATE TYPE class_levels AS ENUM (
    'CP',
    'CE1',
    'CE2',
    'CM1',
    'CM2'
);



GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO teacherapp_admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO teacherapp_admin;

INSERT INTO teachers (id, first_name, last_name, login, email,password) values (1,'Eugénie', 'Coukereau', 'E_Coukereau', 'eugenie_59@hotmail.fr','$2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQbLgtnOoKsWc.6U6H0llP3puzeeEu');
INSERT INTO classes (id, name, teacher_id) values (1, 'Grand-Failly CE2 CM1 CM2 2016-2017',1);
INSERT INTO pupils (id, first_name, last_name, login,password, class_id) values (1,'Milan', 'Balkori', 'M_Balkori', '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQbLgtnOoKsWc.6U6H0llP3puzeeEu',1);
INSERT INTO pupils (id, first_name, last_name, login,password, class_id) values (2,'Tomy', 'Mofrigues', 'T_Mofrigues', '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQbLgtnOoKsWc.6U6H0llP3puzeeEu',1);
