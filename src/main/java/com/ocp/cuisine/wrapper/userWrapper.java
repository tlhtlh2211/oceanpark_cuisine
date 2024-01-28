package com.ocp.cuisine.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class userWrapper {

    private Long id;
    private String name;
    private String email;
    private String status;
    private String bio;
    private String role;
    private String studentID;

    public userWrapper(Long id, String name, String email, String status){
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public userWrapper(Long id, String name, String studentID, String role, String bio, String email, String status){
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.studentID = studentID;
        this.bio = bio;
        this.role = role;
    }
}
