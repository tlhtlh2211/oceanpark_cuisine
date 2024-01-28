package com.ocp.cuisine.restimplement;

import com.ocp.cuisine.constant.CuisineConstants;
import com.ocp.cuisine.wrapper.userWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.ocp.cuisine.rest.UserRest;
import com.ocp.cuisine.service.UserService;
import com.ocp.cuisine.util.CuisineUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImplement implements UserRest {

    @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try{
            return userService.signUp(requestMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<userWrapper>> getAllUser() {
        try{
            return userService.getAllUser();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<userWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            return userService.update(requestMap);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity checkToken() {
        try{
            return userService.checkToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassWord(Map<String, String> requestMap) {
        try{
            return userService.changePassWord(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassWord(Map<String, String> requestMap) {
        try{
            return userService.forgotPassWord(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<userWrapper> getInfo() {
        try{
            return userService.getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<userWrapper>(new userWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
