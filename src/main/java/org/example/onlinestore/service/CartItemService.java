package org.example.onlinestore.service;

import lombok.AllArgsConstructor;
import org.example.onlinestore.entity.Cart;
import org.example.onlinestore.entity.CartItem;
import org.example.onlinestore.entity.Item;
import org.example.onlinestore.repo.CartItemRepo;
import org.example.onlinestore.repo.ItemRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CartItemService {
    private final CartItemRepo cartItemRepo;
    private final ItemRepo itemRepo;

    public void cartLoad(CartItem cartItem){
        List<CartItem> allCartItems = cartItemRepo.findAll();
        CartItem existingCartItem = null;

        for (CartItem acItems: allCartItems){
            if (cartItem.getCartID() == acItems.getCartID() && cartItem.getItemID() == acItems.getItemID()){
                existingCartItem = acItems;
                break;
            }
        }
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity()+1);
            cartItemRepo.save(existingCartItem);
        }
        else {
            cartItem.setQuantity(1);
            cartItemRepo.save(cartItem);
        }
    }

    public List<CartItem> getCartItemsByCart(int cartID){
        return cartItemRepo.findByCartID(cartID);
    }

    public void removeItemFromCart(int cartID, int itemID) {
        CartItem cartItem = cartItemRepo.findByCartIDAndItemID(cartID,itemID);
        cartItem.setQuantity(cartItem.getQuantity()-1);

        if (cartItem.getQuantity() > 0){
            cartItemRepo.save(cartItem);
        }
        else {
            cartItemRepo.delete(cartItem);
        }
    }

    public void completeOrder(Cart cart) {
       List<CartItem> cartItems = cartItemRepo.findByCartID(cart.getCartID());

       for (CartItem cartItem: cartItems){
           Item item = itemRepo.findById(cartItem.getItemID()).get();
           item.setItemQuantity(item.getItemQuantity() - cartItem.getQuantity());

           if (item.getItemQuantity() < 0 ){
               throw new RuntimeException("Order could not be completed, not enough items in stock");
           }

           itemRepo.save(item);
       }

       cartItemRepo.deleteAll(cartItems);
    }
}
