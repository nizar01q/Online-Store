package org.example.onlinestore.service;


import lombok.AllArgsConstructor;
import org.example.onlinestore.entity.Store;
import org.example.onlinestore.repo.StoreRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreService {
    private final StoreRepo storeRepo;


    public void addStore(Store store){
        storeRepo.save(store);
    }

    public void removeStore(Store store){
        storeRepo.deleteById(store.getStoreID());
    }

    public List<Store> showStores(){
        return storeRepo.findAll();
    }

    public Optional<Store> showStoreByID(Store store){
        return storeRepo.findById(store.getStoreID());
    }
}
