package org.example.onlinestore.controller;


import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.Store;
import org.example.onlinestore.service.StoreService;
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
public class StoreController {
    private final StoreService storeService;

    @PostMapping("/createstore")
    public ModelAndView createStore(){
        Store store = new Store();
        storeService.addStore(store);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","New Store");
        mv.addObject("addedOrRemoved","added");
        mv.setViewName("success.jsp");

        return mv;
    }

    @PostMapping("/removestore")
    public ModelAndView deleteStore(@RequestParam("storeID") int storeID){
        Store store = new Store(storeID);
        storeService.removeStore(store);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","Store");
        mv.addObject("addedOrRemoved","removed");
        mv.setViewName("success.jsp");

        return mv;
    }

    @GetMapping("/showstores")
    public ModelAndView showStores(){
        List<Store> storeList = storeService.showStores();

        ModelAndView mv = new ModelAndView();
        mv.addObject("stores",storeList);
        mv.setViewName("allstores.jsp");

        return mv;
    }

    @GetMapping("/getstore")
    public ModelAndView getStore(@RequestParam("storeID") int storeID){
        Store store = new Store(storeID);
        Optional<Store> wantedStore = storeService.showStoreByID(store);
        List<Store> stores = new ArrayList<>();
        wantedStore.ifPresent(stores::add);

        ModelAndView mv = new ModelAndView();
        mv.addObject("stores",stores);
        mv.setViewName("allstores.jsp");

        return mv;
    }
}
