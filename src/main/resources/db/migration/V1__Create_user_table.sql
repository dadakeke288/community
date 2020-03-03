create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(50),
    NAME         VARCHAR(20),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
    primary key (ID)
);
