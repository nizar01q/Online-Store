package org.example.onlinestore.service;


import lombok.AllArgsConstructor;
import org.example.onlinestore.entity.CartItem;
import org.example.onlinestore.entity.Store;
import org.example.onlinestore.repo.ItemRepo;
import org.example.onlinestore.entity.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepo itemRepo;


    public void createItem(Item item){
        itemRepo.save(item);
    }

    public void deleteItem(Item item){
        itemRepo.deleteById(item.getItemID());
    }

    public void remakeItem(Item item){
        itemRepo.save(item);
    }

    public Optional<Item> showItemByID(Item item){
        return itemRepo.findById(item.getItemID());
    }

    public List<Item> showItemsByStoreID(Store store){
        return itemRepo.findByStore(store);
    }

    public List<Item> getItemsForCart(List<CartItem> cartItems){
        List<Item> items = new ArrayList<>();
        for (CartItem cartItem: cartItems){
            Optional<Item> item = itemRepo.findById(cartItem.getItemID());
            item.ifPresent(items::add);
        }
        return items;
    }

    public List<Item> showAllItems() {
        return itemRepo.findAll();
    }

    public Optional<Item> getItemForCart(CartItem cartItem) {
        return itemRepo.findById(cartItem.getItemID());
    }

    public List<Item> searchItems(String query) {
        return itemRepo.findByTitleContainingIgnoreCase(query);
    }

    public List<Item> searchFilters(int minQuantity, int maxQuantity, BigDecimal minPrice, BigDecimal maxPrice, String statusQuery, String titleQuery) {

        int minQ = minQuantity != 0 ? minQuantity : 0;
        int maxQ = maxQuantity != 0 ? maxQuantity : Integer.MAX_VALUE;
        BigDecimal min = minPrice != null ? minPrice : BigDecimal.ZERO;
        BigDecimal max = maxPrice != null ? maxPrice : BigDecimal.valueOf(Long.MAX_VALUE);
        String status = (statusQuery == null || statusQuery.isEmpty()) ? "" : statusQuery;
        String title = (titleQuery == null || titleQuery.isEmpty()) ? "" : titleQuery;


        return itemRepo.findByTitleContainingIgnoreCaseAndPriceBetweenAndItemQuantityBetweenAndItemStatusContainingIgnoreCase(
                title, min, max, minQ, maxQ, status);
}

}
