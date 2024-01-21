package com.ocp.cuisine.restimplement;

import com.ocp.cuisine.constant.cuisineConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.ocp.cuisine.rest.userRest;
import com.ocp.cuisine.service.userService;
import com.ocp.cuisine.util.cuisineUtils;

import java.util.Map;

@RestController
public class userRestImplement implements userRest {

    @Autowired
    userService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try{
            userService.signUp(requestMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        return cuisineUtils.getResponseEntity(cuisineConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
