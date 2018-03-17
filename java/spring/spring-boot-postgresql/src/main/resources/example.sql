DROP TABLE IF EXISTS sample;

CREATE SEQUENCE hibernate_sequence;

CREATE TABLE IF NOT EXISTS sample(
  id BIGINT PRIMARY KEY DEFAULT nextval('hibernate_sequence'),
  name VARCHAR(255),
  description VARCHAR(255),
  created_at TIMESTAMP WITHOUT TIME ZONE,
  updated_at TIMESTAMP WITHOUT TIME ZONE
);