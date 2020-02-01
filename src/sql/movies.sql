--
-- Spring Boot + Groovy Templates Example  (https://www.dariawan.com)
-- Copyright (C) 2020 Dariawan <hello@dariawan.com>
--
-- Creative Commons Attribution-ShareAlike 4.0 International License
--
-- Under this license, you are free to:
-- # Share - copy and redistribute the material in any medium or format
-- # Adapt - remix, transform, and build upon the material for any purpose,
--   even commercially.
--
-- The licensor cannot revoke these freedoms
-- as long as you follow the license terms.
--
-- License terms:
-- # Attribution - You must give appropriate credit, provide a link to the
--   license, and indicate if changes were made. You may do so in any
--   reasonable manner, but not in any way that suggests the licensor
--   endorses you or your use.
-- # ShareAlike - If you remix, transform, or build upon the material, you must
--   distribute your contributions under the same license as the original.
-- # No additional restrictions - You may not apply legal terms or
--   technological measures that legally restrict others from doing anything the
--   license permits.
--
-- Notices:
-- # You do not have to comply with the license for elements of the material in
--   the public domain or where your use is permitted by an applicable exception
--   or limitation.
-- # No warranties are given. The license may not give you all of
--   the permissions necessary for your intended use. For example, other rights
--   such as publicity, privacy, or moral rights may limit how you use
--   the material.
--
-- You may obtain a copy of the License at
--   https://creativecommons.org/licenses/by-sa/4.0/
--   https://creativecommons.org/licenses/by-sa/4.0/legalcode
--
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