-- liquibase formatted sql

-- changeset evgeniyth:1
CREATE TABLE notification_tasks
(
    id                     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    message                TEXT      NOT NULL,
    chat_id                BIGINT    NOT NULL,
    notification_date_time TIMESTAMP NOT NULL
);