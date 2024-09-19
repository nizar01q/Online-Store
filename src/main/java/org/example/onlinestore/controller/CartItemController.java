package org.example.onlinestore.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.Cart;
import org.example.onlinestore.entity.CartItem;
import org.example.onlinestore.entity.Item;
import org.example.onlinestore.entity.User;
import org.example.onlinestore.service.CartItemService;
import org.example.onlinestore.service.CartService;
import org.example.onlinestore.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
public class CartItemController {
    private final CartService cartService;
    private final ItemService itemService;
    private final CartItemService cartItemService;

    @PostMapping("/addtocart")
    public ModelAndView addToCart(@RequestParam("userID") int userID,
                                    @RequestParam("itemID") int itemID){

        User user = new User(userID);
        CartItem cartItem = new CartItem(user.getCart().getCartID(),itemID);
        cartItemService.cartLoad(cartItem);


        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","New item");
        mv.addObject("addedOrRemoved","added to your cart");
        mv.setViewName("success");

        return mv;
    }
}
