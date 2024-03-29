package com.ocp.cuisine.serviceimplement;

import com.google.common.base.Strings;
import com.ocp.cuisine.DAO.CategoryDao;
import com.ocp.cuisine.JWT.JwtFilter;
import com.ocp.cuisine.POJO.Category;
import com.ocp.cuisine.constant.CuisineConstants;
import com.ocp.cuisine.service.CategoryService;
import com.ocp.cuisine.util.CuisineUtils;
import com.ocp.cuisine.wrapper.ProductWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImplement implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validateCategoryMap(requestMap, false)){
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return CuisineUtils.getResponseEntity(CuisineConstants.CATEGORY_ADDED, HttpStatus.OK);
                }
            }
            else{
                return CuisineUtils.getResponseEntity(CuisineConstants.UNAUTHORIZED_ACCESS, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            log.info("Inside if");
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateCategoryMap(requestMap, true)){
                    Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        categoryDao.save(getCategoryFromMap(requestMap, true));
                        return CuisineUtils.getResponseEntity(CuisineConstants.CATEGORY_UPDATED, HttpStatus.OK);
                    }
                    else{
                        return CuisineUtils.getResponseEntity(CuisineConstants.CATEGORY_NOT_EXIST, HttpStatus.OK);
                    }
                }
                return CuisineUtils.getResponseEntity(CuisineConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<List<Category>> getAllOnlineCategory() {
        try{
            log.info("Inside get");
            return new ResponseEntity<List<Category>>(categoryDao.getAllOnlineCategory(),HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllOfflineCategory() {
        try{
            log.info("Inside get");
            return new ResponseEntity<List<Category>>(categoryDao.getAllOfflineCategory(),HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCategoryById(Integer id) {
        try{
            log.info("Inside delete");
            if (jwtFilter.isAdmin()){
                Optional optional = categoryDao.findById(id);
                if (!optional.isEmpty()){
                    categoryDao.deleteById(id);
                    return CuisineUtils.getResponseEntity(CuisineConstants.CATEGORY_DELETED, HttpStatus.OK);
                }
                else return CuisineUtils.getResponseEntity(CuisineConstants.CATEGORY_NOT_EXIST, HttpStatus.OK);
            }
            else return CuisineUtils.getResponseEntity(CuisineConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Category> getCategoryById(Integer id) {
        try{
            return new ResponseEntity<>(categoryDao.getCategoryById(id),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Category(), HttpStatus.BAD_REQUEST);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name") &&
                requestMap.containsKey("status") &&
                requestMap.containsKey("category") &&
                requestMap.containsKey("openTime") &&
                requestMap.containsKey("closeTime") &&
                (requestMap.containsKey("socialMediaLink") || requestMap.containsKey("contactNumber"))
        ){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }
            else return !validateId;
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd){
        Category category = new Category();
        if (isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        category.setStatus(requestMap.get("status"));
        category.setCategory(requestMap.get("category"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
        category.setOpenTime(LocalTime.parse(requestMap.get("openTime"), formatter));
        category.setCloseTime(LocalTime.parse(requestMap.get("closeTime"), formatter));
        category.setContactNumber(requestMap.get("contactNumber"));
        category.setSocialMediaLink(requestMap.get("socialMediaLink"));
        return category;
    }
}
