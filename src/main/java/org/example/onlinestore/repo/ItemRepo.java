package org.example.onlinestore.repo;



import org.example.onlinestore.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item,Integer> {
}


