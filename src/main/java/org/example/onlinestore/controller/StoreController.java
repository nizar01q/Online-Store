package org.example.onlinestore.controller;


import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.Store;
import org.example.onlinestore.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping("/createstore")
    public String createStore(@RequestParam("storeName") String storeName,
                              @RequestHeader(value = "Referer", required = false) String referer,
                              RedirectAttributes redirectAttributes){
        Store store = new Store();
        storeService.addStore(store);

        redirectAttributes.addFlashAttribute("message","New store has been created");

        return "redirect:" + (referer != null ? referer : "appManager");
    }

    @PostMapping("/removestore")
    public ModelAndView deleteStore(@RequestParam("storeID") int storeID){
        Store store = new Store(storeID);
        storeService.removeStore(store);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","Store");
        mv.addObject("addedOrRemoved","removed");
        mv.setViewName("success");

        return mv;
    }

    @GetMapping("/showstores")
    public ModelAndView showStores(){
        List<Store> storeList = storeService.showStores();

        ModelAndView mv = new ModelAndView();
        mv.addObject("stores",storeList);
        mv.setViewName("store/allstores");

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
        mv.setViewName("store/allstores");

        return mv;
    }

    @GetMapping("/storeDeletion")
    public String storeDeletion(){
        return "store/storeDeletion";
    }

    @GetMapping("/storeGet")
    public String storeGet(){
        return "store/storeGet";
    }

    @GetMapping("/allstores")
    public String allstores(){
        return "store/allstores";
    }

    @GetMapping("/manageStores")
    public String manageStores(){
        return "store/manageStores";
    }

    @GetMapping("/storeCreation")
    public String storeCreation(){
        return "store/storeCreation";
    }
}
