package org.example.onlinestore.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.Order;
import org.example.onlinestore.entity.User;
import org.example.onlinestore.repo.OrderRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    public void addOrder(Order order) {
        orderRepo.save(order);
    }

    public void updateStatus(Order order) {
        orderRepo.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Optional<Order> getOrderByID(int orderID) {
        return orderRepo.findById(orderID);
    }

    public void deleteOrder(Order order) {
        orderRepo.delete(order);
    }

    public List<Order> searchOrders(LocalDateTime startDate, LocalDateTime endDate, BigDecimal minPrice, BigDecimal maxPrice, String statusQuery,String addressQuery,String paymentQuery, User user) {
        LocalDateTime start = startDate != null ? startDate : LocalDateTime.of(1970, 1, 1, 0, 0);
        LocalDateTime end = endDate != null ? endDate : LocalDateTime.of(2100, 1, 1, 0, 0);
        BigDecimal min = minPrice != null ? minPrice : BigDecimal.ZERO;
        BigDecimal max = maxPrice != null ? maxPrice : BigDecimal.valueOf(Long.MAX_VALUE);
        String status = (statusQuery == null || statusQuery.isEmpty()) ? "": statusQuery;
        String address = (addressQuery == null || addressQuery.isEmpty()) ? "": addressQuery;
        String payment = (paymentQuery == null || paymentQuery.isEmpty()) ? "": paymentQuery;

        if(user != null){
            return orderRepo.findByOrderStatusContainingAndOrderPriceBetweenAndOrderDateBetweenAndOrderAddressContainingAndOrderPaymentContainingAndUser(status, min, max, start, end,address,payment, user);
        }
        else {
            return orderRepo.findByOrderStatusContainingAndOrderPriceBetweenAndOrderDateBetweenAndOrderAddressContainingAndOrderPaymentContaining(status, min, max, start, end,address,payment);
        }
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepo.findByUser(user);
    }
}
