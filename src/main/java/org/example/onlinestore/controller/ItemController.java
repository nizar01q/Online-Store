package org.example.onlinestore.controller;


import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.Item;
import org.example.onlinestore.entity.Store;
import org.example.onlinestore.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/additem")
    public ModelAndView addItem(@RequestParam("price") BigDecimal price,
                            @RequestParam ("title") String title,
                            @RequestParam ("type") String type,
                            @RequestParam ("description") String description,
                            @RequestParam ("storeID") Store storeID,
                            @RequestParam ("imgURL") MultipartFile img) throws IOException {

        Item item = new Item(price,title,type,description,img.getBytes(),storeID);
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
                                @RequestParam ("price") BigDecimal price,
                                @RequestParam ("title") String title,
                                @RequestParam ("type") String type,
                                @RequestParam ("description") String description,
                                   @RequestParam ("storeID") Store storeID,
                                   @RequestParam ("imgURL") MultipartFile img) throws IOException {

        Item item = new Item(itemID,price,title,type,description,img.getBytes(),storeID);
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
        mv.setViewName("item/viewdetails");

        return mv;
    }

    @GetMapping("/showitems")
    public ModelAndView showItemsByStoreID(@RequestParam("storeID") Store storeID){
        List<Item> itemList = itemService.showItemsByStoreID(storeID);

        for (Item item:itemList){
            String base64Image = Base64.getEncoder().encodeToString(item.getImg());
            item.setImgBase64(base64Image);
        }

        ModelAndView mv = new ModelAndView();

        mv.addObject("items", itemList);
        mv.setViewName("item/electronics");

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

    @GetMapping("/electronics")
    public String electronics(){
        return "item/electronics";
    }

    @GetMapping("/viewdetails")
    public String viewDetails(){
        return "item/viewdetails";
    }
}
