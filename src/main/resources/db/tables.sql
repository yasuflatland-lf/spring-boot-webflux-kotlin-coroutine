create table if not exists todos
(
    id         bigint unsigned auto_increment,
    task       MEDIUMTEXT not null comment 'Task',
    status     boolean  default false comment 'Task status',
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp,
    constraint todo_pk
        primary key (id)
) comment 'Todos' ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
