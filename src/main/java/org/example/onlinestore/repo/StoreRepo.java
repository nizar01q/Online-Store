package org.example.onlinestore.repo;

import org.example.onlinestore.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepo extends JpaRepository<Store,Integer> {
}