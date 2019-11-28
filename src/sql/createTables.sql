CREATE TABLE IF NOT EXISTS roles
(
    id          BIGSERIAL,
    name        VARCHAR(45),
    description TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL,
    login      VARCHAR(45) NOT NULL,
    password   VARCHAR(32),
    surname    VARCHAR(40),
    name       VARCHAR(40),
    patronymic VARCHAR(40),
    id_role    BIGINT,
    active     BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (id_role) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS actions
(
    id          BIGSERIAL,
    name        VARCHAR(254),
    description TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS download_names
(
    id            BIGSERIAL,
    file_type     VARCHAR(10),
    file_number   INT,
    download_date DATE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS codeGVS
(
    id     BIGSERIAL,
    number INT,
    name   VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS invite_org_codes_r009
(
    id   BIGSERIAL,
    code INT,
    name VARCHAR(254),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exclusion_reasons_r010
(
    id   BIGSERIAL,
    code INT,
    name VARCHAR(254),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories_r011
(
    id   BIGSERIAL,
    code INT,
    name VARCHAR(254),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS academic_degree_r012
(
    id   BIGSERIAL,
    code INT,
    name VARCHAR(254),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS specialties_v015
(
    id   BIGSERIAL,
    code INT,
    name VARCHAR(254),
    ref  INT,
    okso INT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS experts
(
    id         BIGSERIAL,
    number     VARCHAR(8)  NOT NULL,
    surname    VARCHAR(40) NOT NULL,
    name       VARCHAR(40) NOT NULL,
    patronymic VARCHAR(40),
    snils      VARCHAR(14) NOT NULL,
    edit_date  DATE        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS emails
(
    id         BIGSERIAL,
    id_expert  BIGINT      NOT NULL,
    email      VARCHAR(45) NOT NULL,
    sort_order INT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id)
);

CREATE TABLE IF NOT EXISTS phones
(
    id         BIGSERIAL,
    id_expert  BIGINT      NOT NULL,
    number     VARCHAR(40) NOT NULL,
    sort_order INT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id)
);

CREATE TABLE IF NOT EXISTS passports
(
    id           BIGSERIAL,
    id_expert    BIGINT      NOT NULL,
    series       VARCHAR(5),
    number       VARCHAR(15) NOT NULL,
    out_org      TEXT,
    out_date     DATE        NOT NULL,
    registration TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id)
);

CREATE TABLE IF NOT EXISTS diplomas
(
    id        BIGSERIAL,
    id_expert BIGINT       NOT NULL,
    special   VARCHAR(128) NOT NULL,
    series    VARCHAR(10),
    number    VARCHAR(30),
    out_org   VARCHAR(254),
    out_date  DATE         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id)
);

CREATE TABLE IF NOT EXISTS training_docs
(
    id         BIGSERIAL,
    id_expert  BIGINT NOT NULL,
    cycle_name VARCHAR(254),
    hours      INT    NOT NULL,
    out_org    VARCHAR(254),
    out_date   DATE   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id)
);

CREATE TABLE IF NOT EXISTS academic_diplomas
(
    id        BIGSERIAL,
    id_expert BIGINT NOT NULL,
    series    VARCHAR(45),
    number    VARCHAR(45),
    out_date  DATE   NOT NULL,
    id_degree BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id),
    FOREIGN KEY (id_degree) REFERENCES academic_degree_r012 (id)
);

CREATE TABLE IF NOT EXISTS invite_orgs
(
    id          BIGSERIAL,
    id_org_code BIGINT NOT NULL,
    name        TEXT,
    address     TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_org_code) REFERENCES invite_org_codes_r009 (id)
);

CREATE TABLE IF NOT EXISTS inclusions
(
    id            BIGSERIAL,
    id_expert     BIGINT NOT NULL,
    date          DATE   NOT NULL,
    id_invite_org BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id),
    FOREIGN KEY (id_invite_org) REFERENCES invite_orgs (id)
);

CREATE TABLE IF NOT EXISTS exclusions
(
    id           BIGSERIAL,
    id_expert    BIGINT NOT NULL,
    id_inclusion BIGINT NOT NULL,
    date         DATE   NOT NULL,
    id_reason    BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id),
    FOREIGN KEY (id_inclusion) REFERENCES inclusions (id) ON DELETE CASCADE,
    FOREIGN KEY (id_reason) REFERENCES exclusion_reasons_r010 (id)
);

CREATE TABLE IF NOT EXISTS workplaces
(
    id        BIGSERIAL,
    id_expert BIGINT       NOT NULL,
    name      VARCHAR(254) NOT NULL,
    position  VARCHAR(254) NOT NULL,
    address   VARCHAR(254),
    PRIMARY KEY (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id)
);

CREATE TABLE IF NOT EXISTS specialties
(
    id           BIGSERIAL,
    id_expert    BIGINT NOT NULL,
    id_specialty BIGINT NOT NULL,
    id_workplace BIGINT NOT NULL,
    id_codeGVS   BIGINT,
    experience   INT    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_specialty) REFERENCES specialties_v015 (id),
    FOREIGN KEY (id_workplace) REFERENCES workplaces (id) ON DELETE SET NULL,
    FOREIGN KEY (id_expert) REFERENCES experts (id),
    FOREIGN KEY (id_codeGVS) REFERENCES codeGVS (id)
);

CREATE TABLE IF NOT EXISTS qualification_docs
(
    id           BIGSERIAL,
    id_expert    BIGINT NOT NULL,
    id_category  BIGINT NOT NULL,
    id_specialty BIGINT NOT NULL,
    series       VARCHAR(45),
    number       VARCHAR(45),
    out_org      VARCHAR(254),
    out_date     DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id),
    FOREIGN KEY (id_category) REFERENCES categories_r011 (id),
    FOREIGN KEY (id_specialty) REFERENCES specialties (id)
);

CREATE TABLE IF NOT EXISTS certificates
(
    id           BIGSERIAL,
    id_specialty BIGINT NOT NULL,
    series       VARCHAR(45),
    number       VARCHAR(45),
    out_date     DATE   NOT NULL,
    out_org      VARCHAR(254),
    PRIMARY KEY (id),
    FOREIGN KEY (id_specialty) REFERENCES specialties (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS expertise
(
    id           BIGSERIAL,
    id_specialty BIGINT NOT NULL,
    count        INT,
    reexp        INT,
    year         INT    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_specialty) REFERENCES specialties (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS logs
(
    id          BIGSERIAL,
    id_user     BIGINT NOT NULL,
    id_action   BIGINT NOT NULL,
    time_action TIMESTAMP,
    id_expert   BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_user) REFERENCES users (id),
    FOREIGN KEY (id_action) REFERENCES actions (id),
    FOREIGN KEY (id_expert) REFERENCES experts (id)
);