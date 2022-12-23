create table if not exists T_TRANSACTION
(
    id varchar(255) default random_uuid() primary key,
    reference varchar(255) NOT NULL,
    user_id varchar(255) NOT NULL,
    amount  DECIMAL NOT NULL,
    created_at timestamp NOT NULL
);