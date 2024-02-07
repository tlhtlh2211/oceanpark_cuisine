package com.ocp.cuisine.rest;

import com.ocp.cuisine.wrapper.ReviewWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/review")
public interface ReviewRest {

    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewReview(@RequestBody(required = true) Map<String, String> requestMap);
    @GetMapping(path = "/getReviewByProductId/{id}")
    public ResponseEntity<List<ReviewWrapper>> getReviewByProductId(@PathVariable Integer id);
    @PostMapping(path = "/update")
    public ResponseEntity<String> updateReview(@RequestBody(required = true) Map<String, String> requestMap);
    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer id);
    @PostMapping(path = "/updateStatus")
    public ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);
}
