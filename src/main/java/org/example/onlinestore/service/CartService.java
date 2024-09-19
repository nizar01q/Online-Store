package org.example.onlinestore.service;


import lombok.AllArgsConstructor;
import org.example.onlinestore.entity.Cart;
import org.example.onlinestore.repo.CartRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepo cartRepo;


    public List<Cart> showCarts(){
        return cartRepo.findAll();
    }

    public Optional<Cart> showCartByID(Cart cart){
        return cartRepo.findById(cart.getCartID());
    }

    public void createCart(Cart cart) {
        cartRepo.save(cart);
    }

    public void deleteCart(Cart cart) {
        cartRepo.deleteById(cart.getCartID());
    }

}
