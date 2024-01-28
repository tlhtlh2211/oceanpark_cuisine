package com.ocp.cuisine.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ocp.cuisine.wrapper.userWrapper;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
@CrossOrigin(origins = "http://127.0.0.1:3000")
public interface UserRest {

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<userWrapper>> getAllUser();

    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/checkToken")
    public ResponseEntity<String> checkToken();

    @PostMapping(path = "/changePassWord")
    public ResponseEntity<String> changePassWord(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/forgotPassWord")
    public ResponseEntity<String> forgotPassWord(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/getInfo")
    public ResponseEntity<userWrapper> getInfo();
}
