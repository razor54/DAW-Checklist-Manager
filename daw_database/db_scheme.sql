--database for daw project

--users default scheme
CREATE TABLE users(
    
    id BIGSERIAL PRIMARY key,
    name VARCHAR(30),
    pass PASSWORD

)

--checklist default scheme 
CREATE TABLE checklit(

    id BIGSERIAL PRIMARY KEY, 
    name varchar(30),
    completionDate DATETIME,
    user_id INT REFERENCES users, 
 
)

-- item default scheme 
CREATE TABLE item(
    
    id BIGSERIAL PRIMARY KEY,
    action_one VARCHAR(30),
    name VARCHAR(30),
    action_two VARCHAR(30),
    checked BOOLEAN,
    list_id INT REFERENCES checklist, 

)