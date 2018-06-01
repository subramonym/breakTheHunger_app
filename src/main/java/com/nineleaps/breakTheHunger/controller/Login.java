package com.nineleaps.breakTheHunger.controller;

import com.nineleaps.breakTheHunger.dto.LoginRequestDto;
import com.nineleaps.breakTheHunger.entities.User;
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
    public ResponseEntity<User> getLogin(@RequestBody(required = true) LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        System.out.println(username);
        System.out.println(password);
        User user = userRepository.findByUserNamePassword(username, password);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }
}
