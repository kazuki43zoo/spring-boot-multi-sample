create table if not exists todo (
    todo_id varchar(36) primary key,
    username varchar(36) not null,
    todo_title varchar(30) not null,
    deadline_date date,
    finished boolean,
    created_at timestamp not null,
    tracking_id varchar(36) not null
);
