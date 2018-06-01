package com.nineleaps.breakTheHunger.repositories;

import com.nineleaps.breakTheHunger.entities.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<User,String> {
}
