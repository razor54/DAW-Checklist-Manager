--database for daw project

--users default scheme
DROP TABLE if EXISTS item ;
DROP TABLE if EXISTS checklist ;
DROP TABLE if EXISTS users ;


CREATE TABLE users(
    
    id BIGSERIAL PRIMARY key,
    name VARCHAR(30),
    pass VARCHAR(30)

);

-- checklist DEFAULT scheme 

CREATE TABLE checklist(

    id BIGSERIAL PRIMARY KEY, 
    name varchar(30),
    completion_date Timestamp,
    user_id INT REFERENCES users
 
);

-- item default scheme 

CREATE TABLE item(
    
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30),
    state varchar(15) check(state='uncompleted' or state='completed'),
    description varchar(256),
    list_id INT REFERENCES checklist

);