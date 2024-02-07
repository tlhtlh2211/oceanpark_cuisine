package com.ocp.cuisine.restimplement;

import com.ocp.cuisine.constant.CuisineConstants;
import com.ocp.cuisine.rest.ReviewRest;
import com.ocp.cuisine.service.ReviewService;
import com.ocp.cuisine.util.CuisineUtils;
import com.ocp.cuisine.wrapper.ReviewWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ReviewRestImplement implements ReviewRest {

    @Autowired
    ReviewService reviewService;
    @Override
    public ResponseEntity<String> addNewReview(Map<String, String> requestMap) {
        try{
            return reviewService.addNewReview(requestMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ReviewWrapper>> getReviewByProductId(Integer id) {
        try{
            return reviewService.getReviewByProductId(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateReview(Map<String, String> requestMap) {
        try{
            return reviewService.updateReview(requestMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteReview(Integer id) {
        try{
            return reviewService.deleteReview(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            return reviewService.updateStatus(requestMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
