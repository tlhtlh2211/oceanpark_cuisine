package com.ocp.cuisine.rest;

import com.ocp.cuisine.POJO.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/category")
public interface CategoryRest {
    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewCategory(@RequestBody(required = true) Map<String, String> requestMap);
    @GetMapping(path = "/get")
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filterValue);
    @PostMapping(path = "/update")
    public ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String, String> requestMap);
    @GetMapping(path = "/getOnline")
    public ResponseEntity<List<Category>> getAllOnlineCategory();
    @GetMapping(path = "/getOffline")
    public ResponseEntity<List<Category>> getAllOfflineCategory();
    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Integer id);
    @GetMapping(path = "/getCategoryById/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id);
}
