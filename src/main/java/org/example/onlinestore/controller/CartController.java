package org.example.onlinestore.controller;


import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.Cart;
import org.example.onlinestore.entity.CartItem;
import org.example.onlinestore.entity.Item;
import org.example.onlinestore.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/allcarts")
    public ModelAndView showCarts(){
        List<Cart> cartList = cartService.showCarts();
        ModelAndView mv = new ModelAndView();
        mv.addObject("carts",cartList);
        mv.setViewName("cart/allcarts");

        return mv;
    }

    @GetMapping("/getcart")
    public ModelAndView getCart(@RequestParam ("cartID") int cartID){
        Cart cart = new Cart(cartID);
        Optional<Cart> wantedCart = cartService.showCartByID(cart);
        List<Cart> carts = new ArrayList<>();
        wantedCart.ifPresent(carts::add);

        ModelAndView mv = new ModelAndView();
        mv.addObject("carts",carts);
        mv.setViewName("cart/allcarts");

        return mv;
    }

    @PostMapping("/addcart")
    public ModelAndView addCart(){
        Cart cart = new Cart();
        cartService.createCart(cart);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","New cart");
        mv.addObject("addedOrRemoved","created");
        mv.setViewName("success");

        return mv;
    }

    @PostMapping("/removecart")
    public ModelAndView removeCart(@RequestParam ("cartID") int cartID){
        Cart cart = new Cart(cartID);
        cartService.deleteCart(cart);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","Cart");
        mv.addObject("addedOrRemoved","removed");
        mv.setViewName("success");

        return mv;
    }

    @GetMapping("/cartDeletion")
    public String cartDeletion(){
        return "cart/cartDeletion";
    }

    @GetMapping("/cartGet")
    public String cartGet(){
        return "cart/cartGet";
    }

    @GetMapping("/manageCarts")
    public String manageCarts(){
        return "cart/manageCarts";
    }
}
