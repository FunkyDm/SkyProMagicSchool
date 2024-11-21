-- liquibase formatted sql

-- changeset funkyDm:1
CREATE INDEX students_name_index ON student (name);

-- changeset funkyDm:6
CREATE INDEX faculties_name_and_color_index ON faculty (name,color);