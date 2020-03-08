create table CUSER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(50),
    NAME         VARCHAR(20),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    BIO           VARCHAR(256),
    AVATAR_URL    VARCHAR(256),
    constraint CUSER_PK
        primary key (ID)
);

create table POST
(
    ID            INT auto_increment,
    TITLE         VARCHAR(100),
    CONTENT       TEXT,
    GMT_CREATE    BIGINT,
    GMT_MODIFIED  BIGINT,
    CREATOR       INT,
    COMMENT_COUNT INT default 0,
    VIEW_COUNT    INT default 0,
    LIKE_COUNT    INT default 0,
    TAG          VARCHAR(256),
    constraint QUESTION_PK
        primary key (ID)
);
