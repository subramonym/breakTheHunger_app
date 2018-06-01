package com.nineleaps.breakTheHunger.repositories;

import com.nineleaps.breakTheHunger.entities.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<UserEntity,String> {
}
