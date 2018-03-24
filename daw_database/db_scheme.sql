--database for daw project

--users default scheme
DROP TABLE if EXISTS item ;
DROP TABLE if EXISTS checklist ;
DROP TABLE if EXISTS users ;


CREATE TABLE users(
    
    id BIGSERIAL PRIMARY key not null,
    name VARCHAR(30),
    pass VARCHAR(30) not null

);

-- checklist DEFAULT scheme 

CREATE TABLE checklist(

    id BIGSERIAL PRIMARY KEY not null, 
    name varchar(30) not null,
    completion_date Timestamp,
    user_id INT REFERENCES users
 
);

-- item default scheme 

CREATE TABLE item(
    
    id BIGSERIAL PRIMARY KEY not null,
    name VARCHAR(30),
    state varchar(15) check(state='uncompleted' or state='completed'),
    description varchar(256),
    list_id INT REFERENCES checklist

);