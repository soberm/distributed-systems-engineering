DROP TABLE IF EXISTS public.live_data;
DROP TABLE IF EXISTS public.vehicle;
DROP TABLE IF EXISTS public.vehicle_manufacturer;
DROP TABLE IF EXISTS public.emergency_service;

DROP SEQUENCE IF EXISTS public.emergency_service_id_seq;
DROP SEQUENCE IF EXISTS public.vehicle_id_seq;
DROP SEQUENCE IF EXISTS public.vehicle_manufacturer_id_seq;
DROP SEQUENCE IF EXISTS public.live_data_id_seq;

-- Sequences

CREATE SEQUENCE public.emergency_service_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.emergency_service_id_seq
    OWNER TO postgres;

CREATE SEQUENCE public.vehicle_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.vehicle_id_seq
    OWNER TO postgres;

CREATE SEQUENCE public.vehicle_manufacturer_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.vehicle_manufacturer_id_seq
    OWNER TO postgres;

CREATE SEQUENCE public.live_data_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.live_data_id_seq
    OWNER TO postgres;

-- Table: public.vehicle_manufacturer

CREATE TABLE public.vehicle_manufacturer
(
    id bigint NOT NULL DEFAULT nextval('vehicle_manufacturer_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT vehicle_manufacturer_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.vehicle_manufacturer
    OWNER to postgres;

-- Table: public.emergency_service

CREATE TABLE public.emergency_service
(
    id bigint NOT NULL DEFAULT nextval('emergency_service_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT emergency_service_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.emergency_service
    OWNER to postgres;

-- Table: public.vehicle

CREATE TABLE public.vehicle
(
    id bigint NOT NULL DEFAULT nextval('vehicle_id_seq'::regclass),
    model_type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    manufacturer_id bigint NOT NULL,
    CONSTRAINT vehicle_pkey PRIMARY KEY (id),
    CONSTRAINT fk4ft9vg4pyeda243dkkus5pu7k FOREIGN KEY (manufacturer_id)
        REFERENCES public.vehicle_manufacturer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.vehicle
    OWNER to postgres;

-- Table: public.live_data

CREATE TABLE public.live_data
(
    id bigint NOT NULL DEFAULT nextval('live_data_id_seq'::regclass),
    crash_event boolean NOT NULL,
    distance_vehicle_ahead double precision NOT NULL,
    distance_vehicle_behind double precision NOT NULL,
    near_crash_event boolean NOT NULL,
    passenger integer NOT NULL,
    lat double precision NOT NULL,
    lon double precision NOT NULL,
    speed double precision NOT NULL,
    vehicle_id bigint NOT NULL,
    CONSTRAINT live_data_pkey PRIMARY KEY (id),
    CONSTRAINT fkq6dski9n6o4twacqucmhtup8a FOREIGN KEY (vehicle_id)
        REFERENCES public.vehicle (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT live_data_distance_vehicle_ahead_check CHECK (distance_vehicle_ahead >= 0::double precision),
    CONSTRAINT live_data_distance_vehicle_behind_check CHECK (distance_vehicle_behind >= 0::double precision),
    CONSTRAINT live_data_passenger_check CHECK (passenger >= 0),
    CONSTRAINT live_data_speed_check CHECK (speed >= 0::double precision)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.live_data
    OWNER to postgres;

-- INSERTS

INSERT INTO vehicle_manufacturer VALUES (default , 'BMW');
INSERT INTO vehicle_manufacturer VALUES (default , 'VW');
INSERT INTO vehicle_manufacturer VALUES (default , 'Tesla');

INSERT INTO emergency_service VALUES (default , 'Police');
INSERT INTO emergency_service VALUES (default , 'Fire Brigade');
INSERT INTO emergency_service VALUES (default , 'Rescue Service');

INSERT INTO vehicle VALUES (default , 'Polo', (SELECT id FROM vehicle_manufacturer WHERE name = 'VW') );
INSERT INTO vehicle VALUES (default , 'Golf', (SELECT id FROM vehicle_manufacturer WHERE name = 'VW') );
INSERT INTO vehicle VALUES (default , '2er Cabrio', (SELECT id FROM vehicle_manufacturer WHERE name = 'BMW') );
INSERT INTO vehicle VALUES (default , '2er Coupe', (SELECT id FROM vehicle_manufacturer WHERE name = 'BMW') );
INSERT INTO vehicle VALUES (default , 'Model S', (SELECT id FROM vehicle_manufacturer WHERE name = 'Tesla') );
INSERT INTO vehicle VALUES (default , 'Model X', (SELECT id FROM vehicle_manufacturer WHERE name = 'Tesla') );
