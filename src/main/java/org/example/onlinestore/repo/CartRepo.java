package org.example.onlinestore.repo;


import org.example.onlinestore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CartRepo extends JpaRepository<Cart,Integer> {
//    Cart findByUserId(int userID);
}
