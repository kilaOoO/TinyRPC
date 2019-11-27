package com.hbs.sample.api;

/**
 * Created by bingsenh on 2019/11/26.
 */
public interface HelloService {
    String hello(String name);
    String hello(Person person);
}
