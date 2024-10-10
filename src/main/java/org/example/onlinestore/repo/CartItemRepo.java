package org.example.onlinestore.repo;

import org.example.onlinestore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Integer> {

    List<CartItem> findByCartID(int cartID);

    CartItem findByCartIDAndItemID(int cartID, int itemID);
}
