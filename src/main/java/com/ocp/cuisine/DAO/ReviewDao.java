package com.ocp.cuisine.DAO;

import com.ocp.cuisine.POJO.Review;
import com.ocp.cuisine.wrapper.ReviewWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewDao extends JpaRepository<Review, Integer> {
    List<ReviewWrapper> getReviewByProductId(@Param("id") Integer id);
}
