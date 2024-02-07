package com.ocp.cuisine.wrapper;

import com.ocp.cuisine.POJO.Category;
import com.ocp.cuisine.POJO.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewWrapper {

    private Integer id;
    private Integer productId;
    private String name;
    private String productName;
    private String description;
    private Integer star;
    private String status;

    public ReviewWrapper(Integer id, String name, Product product, String description, Integer star, String status){
        this.id = id;
        this.productId = product.getId();
        this.productName = product.getName();
        this.name = name;
        this.description = description;
        this.star = star;
        this.status = status;
    }
}
