package org.example.onlinestore.repo;



import org.example.onlinestore.entity.Item;
import org.example.onlinestore.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item,Integer> {
    List<Item> findByStore(Store store);
}


