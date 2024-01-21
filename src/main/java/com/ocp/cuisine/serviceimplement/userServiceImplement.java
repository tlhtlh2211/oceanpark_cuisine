package com.ocp.cuisine.serviceimplement;

import com.ocp.cuisine.DAO.UserDao;
import com.ocp.cuisine.POJO.User;
import com.ocp.cuisine.constant.cuisineConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ocp.cuisine.service.userService;
import com.ocp.cuisine.util.cuisineUtils;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class userServiceImplement implements userService {

    @Autowired
    UserDao userDao;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignup(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return cuisineUtils.getResponseEntity(cuisineConstant.REGISTER_SUCCESS, HttpStatus.OK);
                } else {
                    return cuisineUtils.getResponseEntity(cuisineConstant.EXISTS_EMAIL, HttpStatus.BAD_REQUEST);
                }
            } else {
                return cuisineUtils.getResponseEntity(cuisineConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            log.error("{}",e);
        }
        return cuisineUtils.getResponseEntity(cuisineConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignup(Map<String,String> requestMap){
        return requestMap.containsKey("name")
                && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email")
                && requestMap.containsKey("password");
    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
