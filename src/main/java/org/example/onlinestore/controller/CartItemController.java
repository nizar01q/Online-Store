package org.example.onlinestore.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.Cart;
import org.example.onlinestore.entity.CartItem;
import org.example.onlinestore.entity.Item;
import org.example.onlinestore.entity.User;
import org.example.onlinestore.model.UserPrinciple;
import org.example.onlinestore.service.CartItemService;
import org.example.onlinestore.service.CartService;
import org.example.onlinestore.service.ItemService;
import org.example.onlinestore.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class CartItemController {
    private final UserService userService;
    private final CartService cartService;
    private final ItemService itemService;
    private final CartItemService cartItemService;

    @PostMapping("/addtocart")
    public String addToCart(@RequestParam("itemID") int itemID, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();

        User user = userService.showUserByID(new User(userId)).get();
        CartItem cartItem = new CartItem(user.getCart().getCartID(),itemID);
        cartItemService.cartLoad(cartItem);



        redirectAttributes.addFlashAttribute("message","Item successfully added to your cart");


        return "redirect:/showitems?storeID=1";
    }



    @GetMapping("/getcartitems")
    public ModelAndView getcartItems(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();

        User user = userService.showUserByID(new User(userId)).get();
        List<CartItem> cartItem = cartItemService.getCartItemsByCart(user.getCart().getCartID());
        List<Item> items = itemService.getItemsForCart(cartItem);

        BigDecimal totalPrice = items.stream()
                        .map(Item::getPrice)
                        .reduce(BigDecimal.ZERO,BigDecimal::add);

        ModelAndView mv = new ModelAndView();
        mv.addObject("items",items);
        mv.addObject("totalPrice", totalPrice);
        mv.setViewName("cart/cartitems");

        return mv;
    }


    @PostMapping("/removecartitem")
    public ModelAndView removecartitem(@RequestParam("itemID") int itemID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();

        User user = userService.showUserByID(new User(userId)).get();

        cartItemService.removeItemFromCart(user.getCart().getCartID(),itemID);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","Item");
        mv.addObject("addedOrRemoved","removed from cart");
        mv.setViewName("success");

        return mv;
    }

    @GetMapping("cartitems")
    public String cartItemsPage(){
        return "cart/cartitems";
    }
}
