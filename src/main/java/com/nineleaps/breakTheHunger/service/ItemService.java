package com.nineleaps.breakTheHunger.service;

import com.nineleaps.breakTheHunger.dto.ItemRequestDto;
import com.nineleaps.breakTheHunger.entities.ItemEntity;
import com.nineleaps.breakTheHunger.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {


    @Autowired
    ItemRepository itemRepository;

    public boolean saveItemDetails(ItemRequestDto itemRequestDto) {

        ItemEntity itemEntity = formItemEntity(itemRequestDto);

        ItemEntity item = itemRepository.save(itemEntity);

        if (item != null)
            return true;
        else
            return false;
    }

    private ItemEntity formItemEntity(ItemRequestDto itemRequestDto) {
        ItemEntity itemEntity = new ItemEntity();
        String id = itemRequestDto.getUserId() + ":" + itemRequestDto.getItemName();
        itemEntity.setItemId(id);
        itemEntity.setItemName(itemRequestDto.getItemName());
        itemEntity.setUserId(itemRequestDto.getUserId());
        itemEntity.setType(itemRequestDto.getType());
        itemEntity.setDescription(itemRequestDto.getDescription());
        itemEntity.setPrice(itemRequestDto.getPrice());
        itemEntity.setTime(itemRequestDto.getTime());
        return itemEntity;
    }

    public List<ItemEntity> getAllItems(){
        List<ItemEntity> items = new ArrayList<>();
        itemRepository.findAll()
                .forEach(items::add);
        return items;
    }

//    public List<ItemEntity>getAllItemsByUserId(String userId){
//        List<ItemEntity> items = new ArrayList<>();
//        itemRepository.findItemByUserId(userId)
//                .forEach(items::add);
//        return items;
//    }

    public ItemEntity getItem(String id) {

        return itemRepository.findOne(id);
    }
}
