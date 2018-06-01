package com.nineleaps.breakTheHunger.repositories;

import com.nineleaps.breakTheHunger.entities.ImageEntity;
import com.nineleaps.breakTheHunger.entities.UserEntity;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends ElasticsearchRepository<ImageEntity, String> {


    @Query("{\"bool\" : {\"must\" : {\"term\": {\"itemId\" : \"?0\"}}}}}")
    public ImageEntity findItemByItemId(String itemId);
}
