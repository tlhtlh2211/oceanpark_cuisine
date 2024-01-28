package com.ocp.cuisine.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "SELECT user FROM User user WHERE user.email =:email")

@NamedQuery(name = "User.getAllUser", query = "SELECT new com.ocp.cuisine.wrapper.userWrapper(user.id, user.name, user.email, user.status) FROM User user WHERE user.role='user'")

@NamedQuery(name = "User.updateStatus", query = "UPDATE User user SET user.status =: status WHERE user.id =: id")

@NamedQuery(name = "User.getAllAdmin", query = "SELECT user.email FROM User user WHERE user.role='admin'")

@NamedQuery(name = "User.getInfo", query = "SELECT new com.ocp.cuisine.wrapper.userWrapper(user.id, user.name, user.studentID, user.role, user.bio, user.email, user.status) FROM User user WHERE user.email =:email")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "studentID")
    private String studentID;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

    @Column(name = "bio")
    private String bio;

}
