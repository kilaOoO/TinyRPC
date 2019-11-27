package com.hbs.sample.server;

import com.hbs.sample.api.HelloService;
import com.hbs.sample.api.Person;
import com.hbs.tinyrpc.server.RpcService;

/**
 * Created by bingsenh on 2019/11/26.
 */
@RpcService(value = HelloService.class,version = "sample.hello2")
public class HelloServiceImpl2 implements HelloService {
    @Override
    public String hello(String name) {
        return "你好!" + name;
    }

    @Override
    public String hello(Person person) {
        return "你好!" + person.getFirstName()+" "+person.getLastName();
    }
}