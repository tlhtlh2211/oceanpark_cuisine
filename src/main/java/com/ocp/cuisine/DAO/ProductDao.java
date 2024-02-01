package com.ocp.cuisine.DAO;

import com.ocp.cuisine.POJO.Product;
import com.ocp.cuisine.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<ProductWrapper> getAllProduct();
    @Modifying
    @Transactional
    void updateProductStatus(String status, int id);
    List<ProductWrapper> getProductByCategoryId(@Param("id") Integer id);
    ProductWrapper getProductById(@Param("id") Integer id);
}
