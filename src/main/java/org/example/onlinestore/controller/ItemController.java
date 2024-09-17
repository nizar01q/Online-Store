package org.example.onlinestore.controller;


import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.Item;
import org.example.onlinestore.service.ItemService;
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
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/additem")
    public ModelAndView addItem(@RequestParam ("title") String title,
                          @RequestParam ("type") String type,
                          @RequestParam ("description") String description){

        Item item = new Item(title,type,description);
        itemService.createItem(item);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","New item");
        mv.addObject("addedOrRemoved","added");
        mv.setViewName("success");

        return mv;
    }

    @PostMapping("/removeitem")
    public ModelAndView removeItem(@RequestParam ("itemID") int itemID){

        Item item = new Item(itemID);
        itemService.deleteItem(item);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","Item");
        mv.addObject("addedOrRemoved","removed");
        mv.setViewName("success");

        return mv;
    }

    @PostMapping("/updateitem")
    public ModelAndView updateItem(@RequestParam ("itemID") int itemID,
                             @RequestParam ("title") String title,
                             @RequestParam ("type") String type,
                             @RequestParam ("description") String description){

        Item item = new Item(itemID,title,type,description);
        itemService.remakeItem(item);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","Item");
        mv.addObject("addedOrRemoved","updated");
        mv.setViewName("success");

        return mv;
    }

    @GetMapping("/getitem")
    public ModelAndView getItem(@RequestParam ("itemID") int itemID){
        Item item = new Item(itemID);
        Optional<Item> wantedItem = itemService.showItemByID(item);
        List<Item> items = new ArrayList<>();
        wantedItem.ifPresent(items::add);

        ModelAndView mv = new ModelAndView();
        mv.addObject("items", items);
        mv.setViewName("item/allitems");

        return mv;
    }

    @GetMapping("/showitems")
    public ModelAndView showItems(){
        List<Item> itemList = itemService.showItems();

        ModelAndView mv = new ModelAndView();
        mv.addObject("items", itemList);
        mv.setViewName("item/allitems");

        return mv;
    }

    @GetMapping("/manageItems")
    public String manageItems(){
        return "item/manageItems";
    }

    @GetMapping("/itemAddition")
    public String itemAddition(){
        return "item/itemAddition";
    }

    @GetMapping("/itemDeletion")
    public String itemDeletion(){
        return "item/itemDeletion";
    }

     @GetMapping("/itemGet")
    public String itemGet(){
        return "item/itemGet";
    }

    @GetMapping("/itemUpdate")
    public String itemUpdate(){
        return "item/itemUpdate";
    }

    @GetMapping("/allitems")
    public String allitems(){
        return "item/allitems";
    }
}
