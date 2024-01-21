package com.ocp.cuisine.service;


import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface userService {
    ResponseEntity<String> signUp(Map<String,String> requestMap);
}
