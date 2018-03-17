-- ===============================
-- COMMAND IN PSQL
-- ===============================
-- Create database `bangumi_crawler`
CREATE DATABASE bangumi_crawler OWNER postgres ENCODING 'UTF8';
-- WANING: don't use `postgres` role in production environment

-- Close connection and access `bangumi_crawler`
\c bangumi_crawler;

-- Create tables

-- ===============================
-- use group
-- ===============================

CREATE TABLE IF NOT EXISTS "user" (
  id                         BIGSERIAL PRIMARY KEY,
  username                   TEXT NOT NULL,
  password                   TEXT NOT NULL,
  created_at                 TIMESTAMP WITH TIME ZONE,
  updated_at                 TIMESTAMP WITH TIME ZONE
);

-- ===============================
-- schedule group
-- ===============================

CREATE TABLE IF NOT EXISTS "site" (
  id                         BIGSERIAL PRIMARY KEY,
  name                       TEXT NOT NULL,
  base_url                   TEXT NOT NULL,
  seeds                      TEXT[] NOT NULL,
  interests                  TEXT[] NOT NULL,
  depth                      INTEGER NOT NULL,
  created_at                 TIMESTAMP WITH TIME ZONE,
  updated_at                 TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS "schedule_job" (
  id                         BIGSERIAL PRIMARY KEY,
  name                       TEXT NOT NULL,
  site_ids                   BIGINT[] NOT NULL,
  enabled                    BOOLEAN NOT NULL,
  created_at                 TIMESTAMP WITH TIME ZONE,
  updated_at                 TIMESTAMP WITH TIME ZONE
);

-- ===============================
-- resource group
-- ===============================

CREATE TABLE IF NOT EXISTS "resource" (
  id                         BIGSERIAL PRIMARY KEY,
  name                       TEXT NOT NULL,
  url                        TEXT NOT NULL,
  path                       TEXT,
  file_size                  BIGINT,
  resource_type              TEXT NOT NULL,
  status                     TEXT NOT NULL,
  created_at                 TIMESTAMP WITH TIME ZONE,
  updated_at                 TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS "torrent" (
  -- TODO: add columns
) INHERITS ("resource");

CREATE TABLE IF NOT EXISTS "video" (
  subtitle_group             TEXT NOT NULL,
  resolution                 TEXT NOT NULL,
  duration                   BIGINT NOT NULL
) INHERITS ("resource");

CREATE TABLE IF NOT EXISTS "mp3" (
  duration                   BIGINT
  -- TODO: add columns
) INHERITS ("resource");

CREATE TABLE IF NOT EXISTS "picture" (
  width                   BIGINT,
  height                  BIGINT
) INHERITS ("resource");

-- resource to site: n to 1
ALTER TABLE "resource" ADD FOREIGN KEY (site_id) REFERENCES "site"(id);

-- ===============================
-- Bangumi group
-- ===============================

CREATE TABLE IF NOT EXISTS "bangumi" (
  id                         BIGSERIAL PRIMARY KEY,
  name                       TEXT NOT NULL,
  alias                      TEXT NOT NULL,
  origin_time                TEXT NOT NULL,
  origin_station             TEXT NOT NULL,
  cover_id                   BIGINT, -- picture_id
  latest_episode_id          BIGINT,
  finished                   BOOLEAN NOT NULL DEFAULT FALSE,
  play_at                    DATE NOT NULL,
  created_at                 TIMESTAMP WITH TIME ZONE,
  updated_at                 TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS "link" (
  id                         BIGSERIAL PRIMARY KEY,
  bangumi_id                 BIGINT,
  play_site                  TEXT,
  play_url                   TEXT,
  created_at                 TIMESTAMP WITH TIME ZONE,
  updated_at                 TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS "episode" (
  id                         BIGSERIAL PRIMARY KEY,
  bangumi_id                 BIGINT,
  num                        INTEGER,
  video_id                   BIGINT,
  created_at                 TIMESTAMP WITH TIME ZONE,
  updated_at                 TIMESTAMP WITH TIME ZONE
);

-- for episode
CREATE TABLE IF NOT EXISTS "episode_meta" (
  id                         BIGSERIAL PRIMARY KEY,
  episode_id                 BIGINT NOT NULL,
  link_id                    BIGINT,
  link_play_time             TIMESTAMP WITH TIME ZONE,
  created_at                 TIMESTAMP WITH TIME ZONE,
  updated_at                 TIMESTAMP WITH TIME ZONE
);

-- bangumi to latest_episode: 1 to 1
ALTER TABLE "bangumi" ADD FOREIGN KEY (latest_episode_id) REFERENCES "episode"(id);

-- bangumi to picture: 1 to 1
-- inherit table `picture` no `id`, use `resource`(id) instead
ALTER TABLE "bangumi" ADD FOREIGN KEY (cover_id) REFERENCES "resource"(id);

-- bangumi to link: 1 to n
ALTER TABLE "link" ADD FOREIGN KEY (bangumi_id) REFERENCES "bangumi"(id);

-- bangumi to episode: 1 to n
ALTER TABLE "episode" ADD FOREIGN KEY (bangumi_id) REFERENCES "bangumi"(id);

-- episode to video: 1 to 1
-- inherit table `video` no `id`, use `resource`(id) instead
ALTER TABLE "episode" ADD FOREIGN KEY (video_id) REFERENCES "resource"(id);

-- episode to episode_meta: 1 to n
ALTER TABLE "episode_meta" ADD FOREIGN KEY (episode_id) REFERENCES "episode"(id);

-- episode_meta to link: 1 to 1
ALTER TABLE "episode_meta" ADD FOREIGN KEY (link_id) REFERENCES "link"(id);

-- ===============================
-- References:
-- https://www.postgresql.org/docs/9.6/static/index.html
-- https://www.postgresql.org/docs/9.6/static/sql-syntax.html
-- https://www.postgresql.org/docs/9.6/static/datatype.html
-- ===============================
