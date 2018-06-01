package com.nineleaps.breakTheHunger.controller;

import com.nineleaps.breakTheHunger.dto.ItemRequestDto;
import com.nineleaps.breakTheHunger.dto.ItemUserResponseDto;
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

    @RequestMapping(value = "/item/add",method = RequestMethod.POST)
    @ApiOperation(value = "Register the item", nickname = "Register ItemController")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserRequestDto.class),
            @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseBody
    public ResponseEntity addItem (@RequestBody ItemRequestDto itemRequestDto) {

        ResponseEntity responseEntity = null;

        if(itemRequestDto.getItemName() == null || itemRequestDto.getType() == null
                || itemRequestDto.getDescription() == null || itemRequestDto.getPrice() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Attributes are mandatory cannot be Null");
        }

        Boolean isSuccess = itemService.saveItemDetails(itemRequestDto);

        if(isSuccess) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":" + "\""
                    + "item has been Successfully Registered" + "\"}");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    @RequestMapping(value = "/item/getall",method = RequestMethod.GET)
    @ApiOperation(value = "Get all the item", nickname = "getAll Item")
    @ResponseBody
    public List<ItemEntity> getAllItems () {
        return itemService.getAllItems();
    }

//    @RequestMapping(value = "/item/getall/{userid}",method = RequestMethod.GET)
//    @ApiOperation(value = "Get all the item based on userid", nickname = "getAll with userid ItemController")
//    @ResponseBody
//    public List<ItemEntity> getItemByUserId (@PathVariable String userId) {
//        return itemService.getAllItemsByUserId(userId);
//    }


    @RequestMapping(value = "/item/seller/get/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "Get one item", nickname = "getOne item")
    @ResponseBody
    public ItemEntity getOneItem(@PathVariable String id) {
        return itemService.getItem(id);
    }

    @RequestMapping(value = "/item/buyer/get/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "Get one item and user", nickname = "getOne item and user")
    @ResponseBody
    public ItemUserResponseDto getOneItemWithUserDetail(@PathVariable String id) {
        return itemService.getOneItemWithUserDetail(id);
    }



}
