package com.ocp.cuisine.rest;

import com.ocp.cuisine.POJO.Product;
import com.ocp.cuisine.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewProduct(@RequestBody Map<String, String> requestMap);
    @GetMapping(path = "/get")
    public ResponseEntity<List<ProductWrapper>> getAllProduct();
    @PostMapping(path = "/update")
    public ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);
    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id);
    @PostMapping(path = "/updateStatus")
    public ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);
    @GetMapping(path = "/getProductByCategoryId/{id}")
    public ResponseEntity<List<ProductWrapper>> getProductByCategoryId(@PathVariable Integer id);
    @GetMapping(path = "/getProductById/{id}")
    public ResponseEntity<ProductWrapper> getProductById(@PathVariable Integer id);
    @GetMapping("search")
    public ResponseEntity<List<ProductWrapper>> searchProducts(@RequestParam("query") String query);
}
