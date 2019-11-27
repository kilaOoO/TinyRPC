package com.hbs.sample.server;

import com.hbs.sample.api.HelloService;
import com.hbs.sample.api.Person;
import com.hbs.tinyrpc.server.RpcService;

/**
 * Created by bingsenh on 2019/11/26.
 */
@RpcService(HelloService.class)
public class HelloServiceImpl1 implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello!" + name;
    }

    @Override
    public String hello(Person person) {
        return "Hello!" + person.getFirstName()+" "+person.getLastName();
    }
}
