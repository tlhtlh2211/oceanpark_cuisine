package com.ocp.cuisine.service;


import com.ocp.cuisine.wrapper.userWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> signUp(Map<String,String> requestMap);
    ResponseEntity<String> login(Map<String, String> requestMap);
    ResponseEntity<List<userWrapper>> getAllUser();
    ResponseEntity<String> update(Map<String, String> requestMap);
    ResponseEntity<String> checkToken();
    ResponseEntity<String> changePassWord(Map<String, String> requestMap);
    ResponseEntity<String> forgotPassWord(Map<String, String> requestMap);
    ResponseEntity<userWrapper> getInfo();
}
