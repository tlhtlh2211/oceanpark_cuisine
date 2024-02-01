package com.ocp.cuisine.DAO;

import com.ocp.cuisine.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {
    List<Category> getAllCategory();
    List<Category> getAllOnlineCategory();
    List<Category> getAllOfflineCategory();
}
