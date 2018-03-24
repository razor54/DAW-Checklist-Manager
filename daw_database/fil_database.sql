

delete from Item;
delete from checklist;
delete from users;



insert into users(name ,pass)
values ('Zequinha','123'),
       ('Fonseca','312'),
       ('Matias','9918');


insert into checklist (name,user_id)
        values('muai tai',1);

insert into item (name,state,description ,list_id)
    values('leitura','uncompleted','simples leitura',1) ;
