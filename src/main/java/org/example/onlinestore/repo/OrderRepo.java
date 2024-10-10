package org.example.onlinestore.repo;

import org.example.onlinestore.entity.Order;
import org.example.onlinestore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Integer> {
//    List<Order> findByOrderStatusContainingAndOrderPriceBetweenAndOrderDateBetweenAndUser(String statusQuery, BigDecimal min, BigDecimal max, LocalDateTime start, LocalDateTime end, User user);

//    List<Order> findByOrderStatusContainingAndOrderPriceBetweenAndOrderDateBetween(String statusQuery, BigDecimal min, BigDecimal max, LocalDateTime start, LocalDateTime end);

    List<Order> findByUser(User user);

    List<Order> findByOrderStatusContainingAndOrderPriceBetweenAndOrderDateBetweenAndOrderAddressContainingAndOrderPaymentContainingAndUser(String status, BigDecimal min, BigDecimal max, LocalDateTime start, LocalDateTime end, String address, String payment, User user);

    List<Order> findByOrderStatusContainingAndOrderPriceBetweenAndOrderDateBetweenAndOrderAddressContainingAndOrderPaymentContaining(String status, BigDecimal min, BigDecimal max, LocalDateTime start, LocalDateTime end, String address, String payment);
}
