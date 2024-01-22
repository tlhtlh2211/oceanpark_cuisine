package com.ocp.cuisine.wrapper;

import com.ocp.cuisine.POJO.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ProductWrapper {

    private Integer id;
    private Integer categoryId;
    private String name;
    private String categoryName;
    private String description;
    private Integer price;
    private String status;

    public ProductWrapper(Integer id, String name, Category category, String description, Integer price, String status){
        this.id = id;
        this.categoryId = category.getId();
        this.categoryName = category.getName();
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public ProductWrapper(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public ProductWrapper(Integer id, String name, String description, Integer price){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
