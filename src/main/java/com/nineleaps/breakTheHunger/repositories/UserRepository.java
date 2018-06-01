package com.nineleaps.breakTheHunger.repositories;

import com.nineleaps.breakTheHunger.entities.User;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<User,String> {

    @Query("{\"bool\" : {\"should\" : [{\"term\": {\"name\" : \"?0\"}},{\"term\": {\"password\" : \"?1\"}}]}}}")
    public User findByUserNamePassword(String username,String password);
}
