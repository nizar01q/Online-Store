CREATE TABLE CartItems (
  cartID int,
  itemID int,
  FOREIGN KEY (cartID) references Cart(cartID),
    FOREIGN KEY (itemID) references Item(itemID)
);
