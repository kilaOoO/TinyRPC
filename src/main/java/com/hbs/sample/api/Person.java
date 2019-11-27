package com.hbs.sample.api;

import lombok.Data;

/**
 * Created by bingsenh on 2019/11/26.
 */
@Data
public class Person {
    private String firstName;
    private String lastName;
    public Person(){

    }

    public Person(String firstName,String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
