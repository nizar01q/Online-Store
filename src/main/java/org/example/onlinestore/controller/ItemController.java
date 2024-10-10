package org.example.onlinestore.controller;


import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.Item;
import org.example.onlinestore.entity.Order;
import org.example.onlinestore.entity.Store;
import org.example.onlinestore.entity.User;
import org.example.onlinestore.enums.ItemStatus;
import org.example.onlinestore.service.ItemService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/additem")
    public String addItem(@RequestParam("price") BigDecimal price,
                          @RequestParam ("title") String title,
                          @RequestParam ("type") String type,
                          @RequestParam ("description") String description,
                          @RequestParam ("quantity") int quantity,
                          @RequestParam ("imgURL") MultipartFile img,
                          @RequestHeader(value = "Referer", required = false) String referer,
                          RedirectAttributes redirectAttributes) throws IOException {

        Store store = new Store();

        switch (type){
            case "electronics":
                store.setStoreID(1);
                break;
            case "clothing":
                store.setStoreID(2);
                break;
            case "home":
                store.setStoreID(21);
                break;
        }

        Item item = new Item(price,title,type,description,img.getBytes(),store);
        item.setItemQuantity(quantity);

        if (quantity > 0){
            item.setItemStatus(ItemStatus.IN_STOCK.getStatus());
        }
        else {
            item.setItemStatus(ItemStatus.OUT_OF_STOCK.getStatus());
        }

        itemService.createItem(item);

        redirectAttributes.addFlashAttribute("message","New item has been added");

        return "redirect:" + (referer != null ? referer : "appManager");
    }

    @PostMapping("/removeitem")
    public String removeItem(@RequestParam ("itemID") int itemID,
                             @RequestHeader(value = "Referer", required = false) String referer,
                             RedirectAttributes redirectAttributes){

        Item item = new Item(itemID);
        itemService.deleteItem(item);

        redirectAttributes.addFlashAttribute("message","Item has been removed");

        return "redirect:" + (referer != null ? referer : "appManager");
    }

    @PostMapping("/updateitem")
    public String updateItem(@RequestParam ("itemID") int itemID,
                             @RequestParam ("price") BigDecimal price,
                             @RequestParam ("title") String title,
                             @RequestParam ("type") String type,
                             @RequestParam ("description") String description,
                             @RequestParam ("quantity") int quantity,
                             @RequestParam ("imgURL") MultipartFile img,
                             @RequestHeader(value = "Referer", required = false) String referer,
                             RedirectAttributes redirectAttributes) throws IOException {

        Store store = new Store();

        switch (type){
            case "electronics":
                store.setStoreID(1);
                break;
            case "clothing":
                store.setStoreID(2);
                break;
            case "home":
                store.setStoreID(21);
                break;
        }


        Item item = new Item(itemID,price,title,type,description,img.getBytes(),store);

        item.setItemQuantity(quantity);

        if (quantity > 0){
            item.setItemStatus(ItemStatus.IN_STOCK.getStatus());
        }
        else {
            item.setItemStatus(ItemStatus.OUT_OF_STOCK.getStatus());
        }

        if (img.isEmpty()){
            Item item1 = itemService.showItemByID(new Item(itemID)).get();
            item.setImg(item1.getImg());
        }

        itemService.remakeItem(item);

        redirectAttributes.addFlashAttribute("message","Item has been updated");

        return "redirect:" + (referer != null ? referer : "appManager");
    }

    @GetMapping("/getitem")
    public ModelAndView getItem(@RequestParam ("itemID") int itemID){
        Item item = new Item(itemID);
        Optional<Item> wantedItem = itemService.showItemByID(item);
        List<Item> items = new ArrayList<>();
        wantedItem.ifPresent(items::add);

        for (Item item1:items){
            String base64Image = Base64.getEncoder().encodeToString(item1.getImg());
            item1.setImgBase64(base64Image);
        }

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

    @GetMapping("/showallitems")
    public ModelAndView showAllItems(){
        List<Item> itemList = itemService.showAllItems();

        ModelAndView mv = new ModelAndView();

        mv.addObject("items", itemList);
        mv.setViewName("item/inventoryManagement");

        return mv;
    }

    @GetMapping("/searchItems")               //-----Search bar
    public ModelAndView searchItems(@RequestParam String query){
        List<Item> items = itemService.searchItems(query);

        if(items.isEmpty()){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("message","No matching results");
            modelAndView.setViewName("/home");

            return modelAndView;
        }

        for (Item item: items){
            String base64Image = Base64.getEncoder().encodeToString(item.getImg());
            item.setImgBase64(base64Image);
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("items",items);
        mv.setViewName("item/electronics");

        return mv;
    }

    @GetMapping("/fillItemInfo")
    public ModelAndView fillItemInfo(@RequestParam("itemID") int itemID){
        Item item = itemService.showItemByID(new Item(itemID)).get();

        ModelAndView mv = new ModelAndView();
        mv.addObject("item",item);
        mv.setViewName("item/itemUpdate");

        return mv;
    }

    @GetMapping("/searchItemsFilters")
    public ModelAndView searchItemsFilters(@RequestParam(value = "minQuantity",required = false,defaultValue = "0") int minQuantity ,
                                     @RequestParam(value = "maxQuantity",required = false,defaultValue = "9999") int maxQuantity ,
                                     @RequestParam(value = "minPrice",required = false) BigDecimal minPrice,
                                     @RequestParam(value = "maxPrice",required = false) BigDecimal maxPrice,
                                     @RequestParam(value = "statusQuery",required = false) String statusQuery,
                                     @RequestParam(value = "titleQuery",required = false) String titleQuery){


        List<Item> items = itemService.searchFilters(minQuantity,maxQuantity,minPrice,maxPrice,statusQuery,titleQuery);

        if(items.isEmpty()){
                     ModelAndView modelAndView = new ModelAndView();
                     modelAndView.addObject("error","No items has been found");
                     modelAndView.setViewName("item/inventoryManagement");
                     return modelAndView;
                 }

        ModelAndView mv = new ModelAndView();
        mv.addObject("items",items);
        mv.setViewName("item/inventoryManagement");

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

    @GetMapping("/showitems/itemDeletion")
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

    @GetMapping("/inventoryManagement")
    public String inventoryManagement(){
        return "item/inventoryManagement";
    }
}
