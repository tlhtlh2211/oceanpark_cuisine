package com.ocp.cuisine.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "Review.getReviewByProductId", query = "SELECT new com.ocp.cuisine.wrapper.ReviewWrapper(review.id, review.name, review.product, review.description, review.star, review.status) FROM Review review WHERE review.product.id =: id AND review.status ='true'")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "review")
public class Review implements Serializable {
    public static final long serialVersionUid = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_fk", nullable = false)
    private Product product;

    @Column(name = "description")
    private String description;

    @Column(name = "star")
    private Integer star;

    @Column(name = "status")
    private String status;
}
