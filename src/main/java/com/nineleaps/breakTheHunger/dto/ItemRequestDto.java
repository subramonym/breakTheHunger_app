package com.nineleaps.breakTheHunger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.Timestamp;
import java.util.Date;

public class ItemRequestDto {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("itemName")
    private String itemName;
    @JsonProperty("type")
    private String type;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("time")
    private Date time;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
