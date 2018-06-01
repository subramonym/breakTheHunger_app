package com.nineleaps.breakTheHunger.elasticsearch;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nineleaps.breakTheHunger.entities.ImageEntity;
import com.nineleaps.breakTheHunger.entities.ItemEntity;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ElasticSearchOperation {

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    Client client;

    @Autowired
    ObjectMapper objectMapper;

    public ImageEntity fetchElasticImageEntity(String fileName) {

        ImageEntity imageEntity = null;
        SearchRequestBuilder builder = createSearchBuilderForImageEntity(client);

        try {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            QueryBuilder queryBuilder = QueryBuilders.termsQuery("itemId", fileName);
            boolQueryBuilder.filter(queryBuilder);

            SearchResponse searchResponse = builder.setQuery(boolQueryBuilder).execute().actionGet();

            if (searchResponse.status().getStatus() == 200) {

                try {
                    if (searchResponse.getHits().getTotalHits() != 0) {
                        imageEntity = objectMapper.readValue(
                                searchResponse.getHits().getAt(0).getSourceAsString(), ImageEntity.class);
                    }
                } catch (Exception e) {
                    System.out.println("cannot fetch from db");
                }
            }
        } catch (Exception e) {
            System.out.println("cannot fetch from db");
        }
        return imageEntity;
    }

    private SearchRequestBuilder createSearchBuilderForImageEntity(Client client) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("image_details")
                .setTypes("image").setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        return searchRequestBuilder;
    }

    public List<ItemEntity> fetchElasticItemEntity(String userId) {
        List<ItemEntity> itemEntities = new ArrayList<>();

        SearchRequestBuilder builder = createSearchBuilderForItemEntity(client);

        try {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            QueryBuilder queryBuilder = QueryBuilders.termsQuery("userId", userId);
            boolQueryBuilder.filter(queryBuilder);

            SearchResponse searchResponse = builder.setQuery(boolQueryBuilder).execute().actionGet();

            if (searchResponse.status().getStatus() == 200) {

                try {
                    while (true) {
                        for (SearchHit hit : searchResponse.getHits().getHits()) {

                            String items = hit.getSourceAsString();
                            ItemEntity itemEntity =
                                    mapper.readValue(items, ItemEntity.class);
                            itemEntities.add(itemEntity);
                        }

                        break;



                    }
                } catch (Exception e) {
                    System.out.println("cannot fetch from db");
                }
            }
        } catch (Exception e) {
            System.out.println("cannot fetch from db");
        }
        return itemEntities;
    }

    private SearchRequestBuilder createSearchBuilderForItemEntity(Client client) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("item_details")
                .setTypes("item").setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        return searchRequestBuilder;
    }

}

