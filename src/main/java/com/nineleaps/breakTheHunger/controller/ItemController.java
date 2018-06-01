package com.nineleaps.breakTheHunger.controller;

import com.nineleaps.breakTheHunger.dto.ItemRequestDto;
import com.nineleaps.breakTheHunger.dto.UserRequestDto;
import com.nineleaps.breakTheHunger.entities.ItemEntity;
import com.nineleaps.breakTheHunger.repositories.ItemRepository;
import com.nineleaps.breakTheHunger.service.ItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("breakTheHunger")
public class ItemController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/item/getall",method = RequestMethod.GET)
    @ApiOperation(value = "Get all the item", nickname = "getAll ItemController")
    @ResponseBody
    public List<ItemEntity> getAllItems () {
        return itemService.getAllItems();
    }

    @RequestMapping(value = "/item/getall/{userid}",method = RequestMethod.GET)
    @ApiOperation(value = "Get all the item based on userid", nickname = "getAll with userid ItemController")
    @ResponseBody
    public List<ItemEntity> getItemByUserId (@RequestParam String userId) {
        return itemService.getAllItemsByUserId(userId);
    }

    @RequestMapping(value = "/item/get/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "Get all the item", nickname = "getOne item ItemController")
    @ResponseBody
    public ItemEntity getOneItem(@PathVariable String id) {
        return itemService.getItem(id);
    }

}
