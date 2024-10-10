CREATE SEQUENCE order_id_seq START WITH 1 INCREMENT BY 1;


create table Orders
(
    order_id Int DEFAULT order_id_seq.nextval PRIMARY KEY ,
    order_date date not null,
    order_total number(10, 2) not null,
    order_status varchar2(50) DEFAULT 'Pending'
);

