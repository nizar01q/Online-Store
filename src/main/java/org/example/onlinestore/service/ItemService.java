package org.example.onlinestore.service;


import lombok.AllArgsConstructor;
import org.example.onlinestore.repo.ItemRepo;
import org.example.onlinestore.entity.Item;
import org.springframework.stereotype.Service;

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

    public List<Item> showItems(){
        return itemRepo.findAll();
    }
}
