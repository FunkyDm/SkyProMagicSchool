-- liquibase formatted sql

-- changeset jrembo:1
CREATE TABLE users (
    id      SERIAL,
    email   TEXT
)