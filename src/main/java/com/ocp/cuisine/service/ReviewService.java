package com.ocp.cuisine.service;

import com.ocp.cuisine.wrapper.ReviewWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    ResponseEntity<String> addNewReview(Map<String, String> requestMap);

    ResponseEntity<String> updateReview(Map<String, String> requestMap);

    ResponseEntity<String> deleteReview(Integer id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<List<ReviewWrapper>> getReviewByProductId(Integer id);
}
