CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE tasks (
    task_id UUID PRIMARY KEY,
    title VARCHAR(255),
    date_time TIMESTAMP,
    priority VARCHAR(255),
    user_id UUID,
    CONSTRAINT fk_tasks_users
        FOREIGN KEY (user_id)
        REFERENCES users (id),
    CONSTRAINT ck_tasks_priority
        CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH'))
);

CREATE TABLE descriptions (
    id UUID PRIMARY KEY,
    description VARCHAR(255),
    task_id UUID UNIQUE,
    CONSTRAINT fk_descriptions_tasks
        FOREIGN KEY (task_id)
        REFERENCES tasks (task_id)
);

CREATE TABLE labels (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);
