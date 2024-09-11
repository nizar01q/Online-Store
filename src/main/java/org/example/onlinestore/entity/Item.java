package org.example.onlinestore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_seq")
    @SequenceGenerator(name = "item_id_seq", sequenceName = "item_id_seq", allocationSize = 1)
    private int itemID;
    private String title;
    private String type;
    private String description;

    public Item(int itemID) {
        this.itemID = itemID;
    }

    public Item(String title, String type, String description) {
        this.title = title;
        this.type = type;
        this.description = description;
    }
}


