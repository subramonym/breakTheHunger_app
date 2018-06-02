package com.nineleaps.breakTheHunger.service;

import com.nineleaps.breakTheHunger.dto.UserRequestDto;
import com.nineleaps.breakTheHunger.entities.UserEntity;
import com.nineleaps.breakTheHunger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean saveUserDetails(UserRequestDto userRequestDto) {

        UserEntity userEntity = formUserEntity(userRequestDto);

        UserEntity us = userRepository.save(userEntity);

        if (us != null)
            return true;
        else
            return false;
    }

    private UserEntity formUserEntity(UserRequestDto userRequestDto) {
        UserEntity userEntity = new UserEntity();
        String id = userRequestDto.getName() + ":" + userRequestDto.getMobileNo();
        userEntity.setId(id);
        userEntity.setAddress(userRequestDto.getAddress());
        userEntity.setEmail(userRequestDto.getEmail());
        userEntity.setMobileNo(userRequestDto.getMobileNo());
        userEntity.setPassword(userRequestDto.getPassword());
        userEntity.setName(userRequestDto.getName());
        String geoPoint = userRequestDto.getLattitude() + "," + userRequestDto.getLongitude();
        userEntity.setGeoLocation(geoPoint);
        return userEntity;
    }
}
