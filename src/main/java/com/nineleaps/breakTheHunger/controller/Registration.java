package com.nineleaps.breakTheHunger.controller;

import com.nineleaps.breakTheHunger.dto.UserRequestDto;
import com.nineleaps.breakTheHunger.repositories.UserRepository;
import com.nineleaps.breakTheHunger.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
@RequestMapping("breakTheHunger")
public class Registration {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    @ApiOperation(value = "Register the user for the first time", nickname = "Register User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserRequestDto.class),
            @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseBody
    public ResponseEntity check (@RequestBody UserRequestDto userRequestDto) {

        if(userRequestDto.getName() == null || userRequestDto.getAddress() == null || userRequestDto.getEmail() == null
                || userRequestDto.getLattitude() == null || userRequestDto.getMobileNo() == null
                || userRequestDto.getPassword() == null || userRequestDto.getLongitude() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Attributes are mandatory cannot be Null");
        }

        Boolean isSuccess = userService.saveUserDetails(userRequestDto);

        if(isSuccess) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":" + "\""
                    + "User has been Successfully Registered" + "\"}");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }
}
