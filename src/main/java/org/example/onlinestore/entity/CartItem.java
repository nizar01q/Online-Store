package org.example.onlinestore.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CART_ITEM")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItem {
    @Id
    private int id;

    @Column(name = "CART_CARTID")
    private int cartID;

    @Column(name = "ITEM_ITEMID")
    private int itemID;


    public CartItem(int cartID, int itemID) {
        this.cartID = cartID;
        this.itemID = itemID;
    }
}
