package com.nineleaps.breakTheHunger.service;

import com.nineleaps.breakTheHunger.dto.ItemRequestDto;
import com.nineleaps.breakTheHunger.elasticsearch.ElasticSearchOperation;
import com.nineleaps.breakTheHunger.dto.ItemUserResponseDto;
import com.nineleaps.breakTheHunger.entities.ItemEntity;
import com.nineleaps.breakTheHunger.entities.UserEntity;
import com.nineleaps.breakTheHunger.repositories.ItemRepository;
import com.nineleaps.breakTheHunger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemService {


    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ElasticSearchOperation elasticSearchOperation;

    @Autowired
    UserRepository userRepository;

    public ItemEntity saveItemDetails(ItemRequestDto itemRequestDto) {

            ItemEntity itemEntity = formItemEntity(itemRequestDto);

            ItemEntity item = itemRepository.save(itemEntity);

            if (item != null)
                return item;
            else
                return null;
        }

        private ItemEntity formItemEntity (ItemRequestDto itemRequestDto){
            ItemEntity itemEntity = new ItemEntity();
            String id = itemRequestDto.getUserId() + ":" + itemRequestDto.getItemName();
            itemEntity.setItemId(id);
            itemEntity.setItemName(itemRequestDto.getItemName());
            itemEntity.setUserId(itemRequestDto.getUserId());
            itemEntity.setType(itemRequestDto.getType());
            itemEntity.setDescription(itemRequestDto.getDescription());
            itemEntity.setPrice(itemRequestDto.getPrice());
            Date time = new Date();
            itemEntity.setTime(time);
            return itemEntity;
        }

        public List<ItemEntity> getAllItems () {
            List<ItemEntity> items = new ArrayList<>();
            itemRepository.findAll()
                    .forEach(items::add);
            return items;
        }

        public List<ItemEntity> getAllItemsByUserId (String userId){
            List<ItemEntity> items = new ArrayList<>();
            elasticSearchOperation.fetchElasticItemEntity(userId)
                    .forEach(items::add);
            return items;
        }

        public ItemEntity getItem (String id){

            return itemRepository.findOne(id);
        }

        public ItemUserResponseDto getOneItemWithUserDetail (String id){
            List<Object> itemuser = new ArrayList<>();
            ItemEntity item = itemRepository.findOne(id);
            String userId = item.getUserId();
            UserEntity user = userRepository.findOne(userId);
            return createUserItemObject(item, user);
        }

        private ItemUserResponseDto createUserItemObject (ItemEntity item, UserEntity user){
            ItemUserResponseDto itemuser = new ItemUserResponseDto();
            itemuser.setItemName(item.getItemName());
            itemuser.setType(item.getType());
            itemuser.setDescription(item.getDescription());
            itemuser.setPrice(item.getPrice());
            itemuser.setName(user.getName());
            itemuser.setEmail(user.getEmail());
            itemuser.setAddress(user.getAddress());
            itemuser.setMobileNo(user.getMobileNo());
            return itemuser;
        }
    }
