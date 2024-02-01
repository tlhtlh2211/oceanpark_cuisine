package com.ocp.cuisine.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "Category.getAllCategory", query = "SELECT category FROM Category category WHERE category.id IN (SELECT product.category.id FROM Product product WHERE product.status = 'true')")

@NamedQuery(name = "Category.getAllOnlineCategory", query = "SELECT category FROM Category category WHERE category.status = 'online'")

@NamedQuery(name = "Category.getAllOfflineCategory", query = "SELECT category FROM Category category WHERE category.status = 'offline'")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUiD = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "category")
    private String category;
}

