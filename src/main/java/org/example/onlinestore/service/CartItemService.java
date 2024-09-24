package org.example.onlinestore.service;

import lombok.AllArgsConstructor;
import org.example.onlinestore.entity.CartItem;
import org.example.onlinestore.entity.Item;
import org.example.onlinestore.repo.CartItemRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CartItemService {
    private final CartItemRepo cartItemRepo;

    public void cartLoad(CartItem cartItem){
        cartItemRepo.save(cartItem);
    }

    public List<CartItem> getCartItemsByCart(int cartID){
        return cartItemRepo.findByCartID(cartID);
    }

    public void removeItemFromCart(int cartID, int itemID) {
        CartItem cartItem = cartItemRepo.findFirstByCartIDAndItemID(cartID,itemID);
        cartItemRepo.delete(cartItem);
    }
}
