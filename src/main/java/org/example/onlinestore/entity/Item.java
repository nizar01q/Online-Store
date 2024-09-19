package org.example.onlinestore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ITEM")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_seq")
    @SequenceGenerator(name = "item_id_seq", sequenceName = "item_id_seq", allocationSize = 1)
    private int itemID;
    private long price;
    private String title;
    private String type;
    private String description;
    private String imgURL;

    @ManyToOne()
    @JoinColumn(name = "STORE_STOREID")
    private Store store;

    @ManyToMany(mappedBy = "items")
    private List<Cart> cartList;

    public Item(int itemID) {
        this.itemID = itemID;
    }

    public Item(Store store) {
        this.store = store;
    }

    public Item(int itemID, long price, String title, String type, String description, String imgURL, Store store) {
        this.itemID = itemID;
        this.price = price;
        this.title = title;
        this.type = type;
        this.description = description;
        this.imgURL = imgURL;
        this.store = store;
    }

    public Item(long price, String title, String type, String description, String imgURL, Store store) {
        this.price = price;
        this.title = title;
        this.type = type;
        this.description = description;
        this.imgURL = imgURL;
        this.store = store;
    }
}


