package com.ocp.cuisine.serviceimplement;

import com.ocp.cuisine.DAO.ProductDao;
import com.ocp.cuisine.DAO.ReviewDao;
import com.ocp.cuisine.JWT.JwtFilter;
import com.ocp.cuisine.POJO.Category;
import com.ocp.cuisine.POJO.Product;
import com.ocp.cuisine.POJO.Review;
import com.ocp.cuisine.constant.CuisineConstants;
import com.ocp.cuisine.service.ReviewService;
import com.ocp.cuisine.util.CuisineUtils;
import com.ocp.cuisine.wrapper.ProductWrapper;
import com.ocp.cuisine.wrapper.ReviewWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewServiceImplement implements ReviewService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    ReviewDao reviewDao;
    @Override
    public ResponseEntity<String> addNewReview(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validateReviewMap(requestMap, false)){
                    reviewDao.save(getReviewFromMap(requestMap, false));
                    return CuisineUtils.getResponseEntity(CuisineConstants.REVIEW_ADDED, HttpStatus.OK);
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

    private Review getReviewFromMap(Map<String, String> requestMap, boolean isAdd) {
        Product product = new Product();
        product.setId(Integer.parseInt(requestMap.get("productId")));

        Review review = new Review();
        if (isAdd){
            review.setId(Integer.parseInt(requestMap.get("id")));
        }
        else{
            review.setStatus("true");
        }
        review.setProduct(product);
        review.setName(requestMap.get("name"));
        review.setDescription(requestMap.get("description"));
        review.setStar(Integer.parseInt(requestMap.get("star")));
        review.setStatus("true");

        return review;
    }

    private boolean validateReviewMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name") && requestMap.containsKey("description") && requestMap.containsKey("star")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }
            else return !validateId;
        }
        return false;
    }

    @Override
    public ResponseEntity<String> updateReview(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validateReviewMap(requestMap, true)){
                    Optional<Review> optional = reviewDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        Review review = getReviewFromMap(requestMap, true);
                        review.setStatus(optional.get().getStatus());
                        reviewDao.save(review);
                        return CuisineUtils.getResponseEntity(CuisineConstants.UPDATE_SUCCESS, HttpStatus.OK);
                    }
                    else return CuisineUtils.getResponseEntity(CuisineConstants.REVIEW_NOT_EXIST, HttpStatus.OK);
                }
                else return CuisineUtils.getResponseEntity(CuisineConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            else return CuisineUtils.getResponseEntity(CuisineConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteReview(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        return null;
    }

    @Override
    public ResponseEntity<List<ReviewWrapper>> getReviewByProductId(Integer id) {
        try{
            return new ResponseEntity<>(reviewDao.getReviewByProductId(id),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


}
