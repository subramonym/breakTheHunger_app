package com.nineleaps.breakTheHunger.controller;

import com.nineleaps.breakTheHunger.dto.LoginRequestDto;
import com.nineleaps.breakTheHunger.entities.UserEntity;
import com.nineleaps.breakTheHunger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("breakTheHunger")

public class Login {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserEntity> getLogin(@RequestBody(required = true) LoginRequestDto loginRequestDto) {
        String mobileNo = loginRequestDto.getMobileNo();
        String password = loginRequestDto.getPassword();
        UserEntity user = userRepository.findByUserNamePassword(mobileNo, password);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().body(user);
    }
}
