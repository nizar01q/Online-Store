Alter TABLE USERS
ADD cartID INT;

ALTER TABLE USERS
ADD CONSTRAINT fk_users_cart
foreign key (cartID) references Cart(cartID);
