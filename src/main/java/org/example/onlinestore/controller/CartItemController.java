package org.example.onlinestore.controller;

import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;


@Controller
@RequiredArgsConstructor
public class CartItemController {
    private final UserService userService;
    private final CartService cartService;
    private final ItemService itemService;
    private final CartItemService cartItemService;

    @PostMapping("/addtocart")
    public String addToCart(@RequestParam("itemID") int itemID,
                            @RequestParam("quantity") int quantity
                            ,@RequestHeader(value = "Referer", required = false) String referer
                            ,RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();

        User user = userService.showUserByID(new User(userId)).get();
        CartItem cartItem = new CartItem(user.getCart().getCartID(),itemID);

        for (int i = 0;i < quantity; i++) {
            cartItemService.cartLoad(cartItem);
        }

        redirectAttributes.addFlashAttribute("message","Item successfully added to your cart");

        return "redirect:" + (referer != null ? referer : "item/electronics");
    }



    @GetMapping("/getcartitems")
    public ModelAndView getcartItems(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();

        User user = userService.showUserByID(new User(userId)).get();
        List<CartItem> cartItems = cartItemService.getCartItemsByCart(user.getCart().getCartID());
        List<Item> items = itemService.getItemsForCart(cartItems);

        Map<Integer, Integer> itemQuantities = new HashMap<>();
            for (CartItem cartItem : cartItems) {
                itemQuantities.put(cartItem.getItemID(), cartItem.getQuantity());
            }

            for (Item item : items) {
                String base64Image = Base64.getEncoder().encodeToString(item.getImg());
                item.setImgBase64(base64Image);
            }

        BigDecimal totalPrice = items.stream()
                .map(item -> {
                    int quantity = itemQuantities.get(item.getItemID());
                    BigDecimal price = item.getPrice();
                    return price.multiply(BigDecimal.valueOf(quantity));})
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        ModelAndView mv = new ModelAndView();
        mv.addObject("items", items);
        mv.addObject("itemQuantities", itemQuantities);
        mv.addObject("totalPrice", totalPrice);
        mv.setViewName("cart/cartitems");

        return mv;
    }


    @PostMapping("/removecartitem")
    public String removecartitem(@RequestParam("itemID") int itemID,
                                       @RequestHeader(value = "Referer", required = false) String referer, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();
        User user = userService.showUserByID(new User(userId)).get();

        cartItemService.removeItemFromCart(user.getCart().getCartID(),itemID);

        redirectAttributes.addFlashAttribute("message","Item removed from your cart");

        return "redirect:" + (referer != null ? referer : "item/electronics");
    }

    @GetMapping("cartitems")
    public String cartItemsPage(){
        return "cart/cartitems";
    }
}
