package com.ocp.cuisine.restimplement;

import com.ocp.cuisine.POJO.Category;
import com.ocp.cuisine.POJO.Product;
import com.ocp.cuisine.constant.CuisineConstants;
import com.ocp.cuisine.rest.CategoryRest;
import com.ocp.cuisine.rest.ProductRest;
import com.ocp.cuisine.service.ProductService;
import com.ocp.cuisine.util.CuisineUtils;
import com.ocp.cuisine.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductRestImplement implements ProductRest {

    @Autowired
    ProductService productService;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            return productService.addNewProduct(requestMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try{
            return productService.getAllProduct();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            return productService.updateProduct(requestMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            return productService.deleteProduct(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            return productService.updateStatus(requestMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getProductByCategoryId(Integer id) {
        try{
            return productService.getProductByCategoryId(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try{
            return productService.getProductById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<ProductWrapper>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> searchProducts(String query) {
        try{
            return  productService.searchProducts(query);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
