package org.example.onlinestore.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.CartItem;
import org.example.onlinestore.entity.Order;
import org.example.onlinestore.entity.User;
import org.example.onlinestore.enums.OrderStatus;
import org.example.onlinestore.model.UserPrinciple;
import org.example.onlinestore.service.CartItemService;
import org.example.onlinestore.service.ItemService;
import org.example.onlinestore.service.OrderService;
import org.example.onlinestore.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final CartItemService cartItemService;
    private final OrderService orderService;

    @PostMapping("/createorder")
    public String createOrder(@RequestParam("orderTotal")BigDecimal orderTotal,
                              @RequestParam("payment") String payment,
                              @RequestParam("address") String address,
                              @RequestHeader(value = "Referer", required = false) String referer, RedirectAttributes redirectAttributes){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();
        User user = userService.showUserByID(new User(userId)).get();

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderPrice(orderTotal);
        order.setOrderStatus(OrderStatus.PENDING.getStatus());
        order.setUser(user);
        order.setOrderAddress(address);
        order.setOrderPayment(payment);
        orderService.addOrder(order);

        redirectAttributes.addFlashAttribute("message","Order has been placed");

        return "redirect:" + (referer != null ? referer : "/home") ;
    }

    @PostMapping("/cancelorder")
    public String cancelOrder(@RequestParam("orderID")int orderID,
                              @RequestHeader(value = "Referer", required = false) String referer,
                              RedirectAttributes redirectAttributes){

        Optional<Order> order = orderService.getOrderByID(orderID);
        order.get().setOrderStatus(OrderStatus.CANCELLED.getStatus());
        orderService.updateStatus(order.get());

        redirectAttributes.addFlashAttribute("message","Order has been cancelled");

        return "redirect:" + (referer != null ? referer : "/appManager") ;
    }

    @PostMapping("/processorder")
    public String processOrder(@RequestParam("orderID")int orderID,
                              @RequestHeader(value = "Referer", required = false) String referer,
                              RedirectAttributes redirectAttributes){

        Optional<Order> order = orderService.getOrderByID(orderID);
        order.get().setOrderStatus(OrderStatus.PROCESSING.getStatus());
        orderService.updateStatus(order.get());

        redirectAttributes.addFlashAttribute("message","Order set to processing state");

        return "redirect:" + (referer != null ? referer : "/appManager") ;
    }

    @PostMapping("/deliverorder")
    public String deliverOrder(@RequestParam("orderID")int orderID,
                              @RequestHeader(value = "Referer", required = false) String referer,
                              RedirectAttributes redirectAttributes){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();
        User user = userService.showUserByID(new User(userId)).get();
        try {
            Optional<Order> order = orderService.getOrderByID(orderID);
            order.get().setOrderStatus(OrderStatus.DELIVERED.getStatus());
            cartItemService.completeOrder(user.getCart());
            orderService.updateStatus(order.get());

            redirectAttributes.addFlashAttribute("message","Order has been delivered");

            return "redirect:" + (referer != null ? referer : "/appManager") ;
        }
        catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());

            return "redirect:" + (referer != null ? referer : "/appManager") ;
        }



    }

    @PostMapping("/removeorder")
    public String removeOrder(@RequestParam("orderID")int orderID,
                              @RequestHeader(value = "Referer", required = false) String referer,
                              RedirectAttributes redirectAttributes){

        Optional<Order> order = orderService.getOrderByID(orderID);
        orderService.deleteOrder(order.get());

        redirectAttributes.addFlashAttribute("message","Order has been removed");

        return "redirect:" + (referer != null ? referer : "/appManager") ;
    }

    @PostMapping("/requestrefund")
    public String requestRefund(@RequestParam("orderID")int orderID,
                              @RequestHeader(value = "Referer", required = false) String referer,
                              RedirectAttributes redirectAttributes){

        Optional<Order> order = orderService.getOrderByID(orderID);
        order.get().setOrderStatus(OrderStatus.REQUEST_REFUND.getStatus());
        orderService.updateStatus(order.get());

        redirectAttributes.addFlashAttribute("message","Refund requested, waiting for admin approval...");

        return "redirect:" + (referer != null ? referer : "/appManager") ;
    }

    @PostMapping("/refundorder")
    public String refundOrder(@RequestParam("orderID")int orderID,
                              @RequestHeader(value = "Referer", required = false) String referer,
                              RedirectAttributes redirectAttributes){

        Optional<Order> order = orderService.getOrderByID(orderID);
        order.get().setOrderStatus(OrderStatus.REFUNDED.getStatus());
        orderService.updateStatus(order.get());

        redirectAttributes.addFlashAttribute("message","Order refunded");

        return "redirect:" + (referer != null ? referer : "/appManager") ;
    }

    @GetMapping("/showorders")
    public ModelAndView showOrders(){
        List<Order> orderList = orderService.getAllOrders();

        ModelAndView mv = new ModelAndView();
        mv.addObject("orders",orderList);
        mv.setViewName("order/orderManagement");
        return mv;
    }

    @GetMapping("/searchorders")
    public ModelAndView searchOrders(@RequestParam(value = "startDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate ,
                                     @RequestParam(value = "endDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate ,
                                     @RequestParam(value = "minPrice",required = false) BigDecimal minPrice,
                                     @RequestParam(value = "maxPrice",required = false) BigDecimal maxPrice,
                                     @RequestParam(value = "statusQuery",required = false) String statusQuery,
                                     @RequestParam(value = "addressQuery",required = false) String addressQuery,
                                     @RequestParam(value = "paymentQuery",required = false) String paymentQuery,
                                     @RequestParam(value = "userID", required = false)Integer userID){
        User user = null;

        if (userID != null) {
             Optional<User> optionalUser = userService.showUserByID(new User(userID));
             if(optionalUser.isPresent()){
                 user = optionalUser.get();
             }
             else {
                 ModelAndView modelAndView = new ModelAndView();
                 modelAndView.addObject("error","This user does not exist");
                 modelAndView.setViewName("order/orderManagement");
                 return modelAndView;
             }
        }

        List<Order> orders = orderService.searchOrders(startDate,endDate,minPrice,maxPrice,statusQuery,addressQuery,paymentQuery,user);

        if(orders.isEmpty()){
                     ModelAndView modelAndView = new ModelAndView();
                     modelAndView.addObject("error","No orders for this user has been found");
                     modelAndView.setViewName("order/orderManagement");
                     return modelAndView;
                 }

        ModelAndView mv = new ModelAndView();
        mv.addObject("orders",orders);
        mv.setViewName("order/orderManagement");

        return mv;
    }

    @GetMapping("/currentUserOrderHistory")
    public ModelAndView currentUserOrderHistory(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();

        User user = userService.showUserByID(new User(userId)).get();
        List<Order> orders = orderService.getOrdersByUser(user);


        ModelAndView mv = new ModelAndView();
        mv.addObject("orders", orders);
        mv.setViewName("order/orderHistory");
        return mv;
    }

    @GetMapping("/userOrderHistory")
    public ModelAndView userOrderHistory(@RequestParam("userID") int userID){
        User user = userService.showUserByID(new User(userID)).get();
        List<Order> orders = orderService.getOrdersByUser(user);


        ModelAndView mv = new ModelAndView();
        mv.addObject("orders", orders);
        mv.setViewName("order/orderHistory");
        return mv;
    }

    @GetMapping("/checkout")
    public ModelAndView checkout(@RequestParam("orderTotal") BigDecimal orderTotal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();
        User user = userService.showUserByID(new User(userId)).get();

        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.addObject("totalPrice",orderTotal);
        mv.setViewName("order/checkout");

        return mv;
    }

    @GetMapping("/allOrders")
    public String allOrders(){
        return "order/orderManagement";
    }
}
