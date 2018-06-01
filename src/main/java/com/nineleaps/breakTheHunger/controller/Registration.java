package com.nineleaps.breakTheHunger.controller;

import com.nineleaps.breakTheHunger.entities.User;
import com.nineleaps.breakTheHunger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Registration {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/check",method = RequestMethod.GET)
    public void check () {

        User user = new User();
        user.setId("1");
        user.setName("subbu");
        userRepository.save(user);
        System.out.println("its working");
    }
}
