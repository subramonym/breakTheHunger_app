package com.nineleaps.breakTheHunger.repositories;

import com.nineleaps.breakTheHunger.entities.ItemEntity;
import com.nineleaps.breakTheHunger.entities.UserEntity;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemRepository extends ElasticsearchRepository<ItemEntity,String>{

    @Query("{\"bool\" : {\"must\" : {\"term\": {\"userId\" : \"?0\"}}}}}")
    public UserEntity findItemByUserId(String userId);
}
