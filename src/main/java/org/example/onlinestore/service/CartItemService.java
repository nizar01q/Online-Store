package org.example.onlinestore.service;

import lombok.AllArgsConstructor;
import org.example.onlinestore.entity.CartItem;
import org.example.onlinestore.repo.CartItemRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartItemService {
    private final CartItemRepo cartItemRepo;

    public void cartLoad(CartItem cartItem){
        cartItemRepo.save(cartItem);
    }
}
