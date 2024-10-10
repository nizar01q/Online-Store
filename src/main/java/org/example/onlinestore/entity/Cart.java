package org.example.onlinestore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name= "CART")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_seq")
    @SequenceGenerator(name = "cart_id_seq", sequenceName = "cart_id_seq", allocationSize = 1)
    private int cartID;

    @ManyToMany
    @JoinTable(
            name = "CART_ITEM",
            joinColumns = @JoinColumn(name = "CART_CARTID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ITEMID")
    )
    private List<Item> items;


    public Cart(int cartID) {
        this.cartID = cartID;
    }
}
