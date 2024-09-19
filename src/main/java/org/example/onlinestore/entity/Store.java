package org.example.onlinestore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STORE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_id_seq")
    @SequenceGenerator(name = "store_id_seq", sequenceName = "store_id_seq", allocationSize = 1)
    private int storeID;

    @OneToMany(mappedBy = "store")
    List<Item> items = new ArrayList<>();

    public Store(int storeID) {
        this.storeID = storeID;
    }
}
