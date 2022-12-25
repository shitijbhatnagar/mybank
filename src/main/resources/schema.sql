create table if not exists T_TRANSACTION
(
    id varchar(255) primary key,
    reference varchar(255) NOT NULL,
    user_id varchar(255) NOT NULL,
    amount  DECIMAL NOT NULL,
    created_at timestamp NOT NULL
);

create table if not exists T_REFERRAL
(
    id varchar(255) primary key,
    name varchar(255) NOT NULL
);