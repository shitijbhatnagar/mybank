create table if not exists t_transaction
(
    id uuid default random_uuid() primary key,
    reference varchar(255) NOT NULL,
    user_id varchar(255) NOT NULL,
    amount  DECIMAL NOT NULL,
    datetime timestamp NOT NULL
);