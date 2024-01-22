package com.ocp.cuisine.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "Product.getAllProduct", query = "SELECT new com.ocp.cuisine.wrapper.ProductWrapper(product.id, product.name, product.category, product.description, product.price, product.status) FROM Product product WHERE product.status = 'true'")

@NamedQuery(name = "Product.updateProductStatus", query = "UPDATE Product product SET product.status =: status WHERE product.id =: id")

@NamedQuery(name = "Product.getProductByCategoryId", query = "SELECT new com.ocp.cuisine.wrapper.ProductWrapper(product.id, product.name) FROM Product product WHERE product.category.id =:id AND product.status ='true'")

@NamedQuery(name = "Product.getProductById", query = "SELECT new com.ocp.cuisine.wrapper.ProductWrapper(product.id, product.name, product.description, product.price) FROM Product product WHERE product.id =:id AND product.status ='true'")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "product")
public class Product implements Serializable {
    public static final long serialVersionUid = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private String status;
}

