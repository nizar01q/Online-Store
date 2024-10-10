package org.example.onlinestore.entity;


import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "CART_CARTID")
    private int cartID;

    @Column(name = "ITEM_ITEMID")
    private int itemID;

    @Column(name = "QUANTITY" )
    private int quantity;


    public CartItem(int cartID, int itemID) {
        this.cartID = cartID;
        this.itemID = itemID;
    }
}
