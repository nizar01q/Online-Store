package org.example.onlinestore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    private BigDecimal price;
    private String title;
    private String type;
    private String description;

    @Lob
    @Column(name = "IMG")
    private byte[] img;

    @Transient
    private String imgBase64;

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

    public Item(int itemID, BigDecimal price, String title, String type, String description, byte[] imgURL, Store store) {
        this.itemID = itemID;
        this.price = price;
        this.title = title;
        this.type = type;
        this.description = description;
        this.img = imgURL;
        this.store = store;
    }

    public Item(BigDecimal price, String title, String type, String description, byte[] imgURL, Store store) {
        this.price = price;
        this.title = title;
        this.type = type;
        this.description = description;
        this.img = imgURL;
        this.store = store;
    }
}


