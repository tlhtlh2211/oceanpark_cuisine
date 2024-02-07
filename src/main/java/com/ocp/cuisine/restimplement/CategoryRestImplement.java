package com.ocp.cuisine.restimplement;

import com.ocp.cuisine.POJO.Category;
import com.ocp.cuisine.constant.CuisineConstants;
import com.ocp.cuisine.rest.CategoryRest;
import com.ocp.cuisine.service.CategoryService;
import com.ocp.cuisine.util.CuisineUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImplement implements CategoryRest {

    @Autowired
    CategoryService categoryService;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            return categoryService.addNewCategory(requestMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            return categoryService.getAllCategory(filterValue);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            return categoryService.updateCategory(requestMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllOnlineCategory() {
        try{
            return categoryService.getAllOnlineCategory();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllOfflineCategory() {
        try{
            return categoryService.getAllOfflineCategory();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCategoryById(Integer id) {
        try{
            return categoryService.deleteCategoryById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Category> getCategoryById(Integer id) {
        try{
            return categoryService.getCategoryById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Category(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
