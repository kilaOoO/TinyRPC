package com.hbs.sample.client;

import com.hbs.sample.api.HelloService;
import com.hbs.tinyrpc.client.RpcProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bingsenh on 2019/11/26.
 */
public class HelloClient1 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        RpcProxy rpcProxy = context.getBean(RpcProxy.class);
        HelloService helloService1 =  rpcProxy.create(HelloService.class);
        String result1 = helloService1.hello("World");
        System.out.println(result1);

        HelloService helloService2 = rpcProxy.create(HelloService.class,"sample.hello2");
        String result2 = helloService2.hello("世界");
        System.out.println(result2);
        System.exit(0);

    }
}
