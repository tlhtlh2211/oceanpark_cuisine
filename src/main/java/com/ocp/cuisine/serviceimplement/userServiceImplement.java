package com.ocp.cuisine.serviceimplement;

import com.google.common.base.Strings;
import com.ocp.cuisine.DAO.UserDao;
import com.ocp.cuisine.JWT.CustomUserDetailsService;
import com.ocp.cuisine.JWT.JwtFilter;
import com.ocp.cuisine.JWT.JwtUtils;
import com.ocp.cuisine.POJO.User;
import com.ocp.cuisine.constant.CuisineConstants;
import com.ocp.cuisine.wrapper.userWrapper;
import com.ocp.cuisine.util.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.ocp.cuisine.service.UserService;
import com.ocp.cuisine.util.CuisineUtils;

import java.util.*;

@Service
@Slf4j
public class userServiceImplement implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignup(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CuisineUtils.getResponseEntity(CuisineConstants.REGISTER_SUCCESS, HttpStatus.OK);
                } else {
                    return CuisineUtils.getResponseEntity(CuisineConstants.EXISTS_EMAIL, HttpStatus.BAD_REQUEST);
                }
            } else {
                return CuisineUtils.getResponseEntity(CuisineConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            log.error("{}",e);
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login {}", requestMap);
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),
                            requestMap.get("password"))
            );
            if (authenticate.isAuthenticated()){
                if (customUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token:\""+"\""+
                            jwtUtils.generateToken(customUserDetailsService.getUserDetail().getEmail(),
                                    customUserDetailsService.getUserDetail().getRole()) + "\"}",
                            HttpStatus.OK
                    );
                }
                else{
                    return new ResponseEntity<String>("{\"message:\""+"\"Waiting for approval\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e){
            log.error("{}",e);
        }
        return new ResponseEntity<String>("{\"message:\"}"+"\"Bad Credentials\"",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<userWrapper>> getAllUser() {
        try{
            if (jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<userWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                    return CuisineUtils.getResponseEntity(CuisineConstants.UPDATE_SUCCESS, HttpStatus.OK);
                }
                else{
                    return CuisineUtils.getResponseEntity(CuisineConstants.ID_NOT_EXIST, HttpStatus.OK);
                }
            }
            else{
                return CuisineUtils.getResponseEntity(CuisineConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return CuisineUtils.getResponseEntity("True", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassWord(Map<String, String> requestMap) {
        try{
            User userObject = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (!userObject.equals(null)){
                if (userObject.getPassword().equals(requestMap.get("oldPassWord"))){
                    userObject.setPassword(requestMap.get("newPassWord"));
                    userDao.save(userObject);
                    return CuisineUtils.getResponseEntity(CuisineConstants.PASSWORD_UPDATE, HttpStatus.OK);
                }
                return CuisineUtils.getResponseEntity(CuisineConstants.INCORRECT_OLD_PASSWORD, HttpStatus.BAD_REQUEST);
            }
            return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassWord(Map<String, String> requestMap) {
        try{
            User user = userDao.findByEmail(requestMap.get("email"));
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())){
                emailUtils.forgotMail(user.getEmail(), CuisineConstants.CREDENTIALS, user.getPassword());
            }
            return CuisineUtils.getResponseEntity(CuisineConstants.CREDENTIALS_EMAIL, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignup(Map<String,String> requestMap){
        return requestMap.containsKey("name")
                && requestMap.containsKey("studentID")
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
        user.setStudentID(requestMap.get("studentID"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if (status != null && status.equalsIgnoreCase("true")){
            emailUtils.sendMessage(jwtFilter.getCurrentUser(), "Account Approved", "User" + user + "\n is approved by \n ADMIN:-" + jwtFilter.getCurrentUser(), allAdmin);
        }
        else{
            emailUtils.sendMessage(jwtFilter.getCurrentUser(), "Account Disabled", "User" + user + "\n is disabled by \n ADMIN:-" + jwtFilter.getCurrentUser(), allAdmin);
        }
    }
}
