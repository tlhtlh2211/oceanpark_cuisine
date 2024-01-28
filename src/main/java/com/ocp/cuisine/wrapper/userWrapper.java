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

    public userWrapper(Long id, String name, String email, String status){
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }
}
