CREATE TABLE movie
(
    id bigserial NOT NULL,
    title character varying(255) NOT NULL,
    release_year integer,
    runtime_minutes integer,
    tags character varying(255),
    lang character varying(50),
    country character varying(50),
    CONSTRAINT movie_pkey PRIMARY KEY (id)
);

ALTER TABLE movie OWNER TO barista;

insert into movie (title, release_year, runtime_minutes, tags, lang, country)
values 
('Saving Private Ryan', 1998, 169, 'Drama, War', 'en', 'USA'),
('Gladiator', 2000, 155, 'Action, Adventure, Drama', 'en', 'USA'),
('Schindler''s List', 1993, 195, 'Biography, Drama, History', 'en', 'USA'),
('Blood Diamond', 2006, 143, 'Adventure, Drama, Thriller', 'en', 'USA'),
('Black Hawk Down', 2001, 144, 'Drama, History, War', 'en', 'USA'),
('Hidden Figures', 2016, 127, 'Biography, Drama, History', 'en', 'USA'),
('Patriots Day', 2016, 133, 'Action, Crime, Drama', 'en', 'USA'),
('Hacksaw Ridge', 2016, 139, 'Biography, Drama, History', 'en', 'USA');

-- truncate table movie;
-- select * from movie;