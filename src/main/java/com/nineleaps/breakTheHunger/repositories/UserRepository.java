package com.nineleaps.breakTheHunger.repositories;

import org.springframework.data.elasticsearch.annotations.Query;
import com.nineleaps.breakTheHunger.entities.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<UserEntity,String> {

    @Query("{\"bool\" : {\"must\" : [{\"term\": {\"mobileNo\" : \"?0\"}},{\"term\": {\"password\" : \"?1\"}}]}}}")
    public UserEntity findByUserNamePassword(String username, String password);
}

