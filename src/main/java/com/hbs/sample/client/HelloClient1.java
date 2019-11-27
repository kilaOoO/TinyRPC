package com.hbs.sample.client;

import com.hbs.sample.api.HelloService;
import com.hbs.tinyrpc.client.RpcProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by bingsenh on 2019/11/26.
 */
public class HelloClient1 {
    @Autowired
    private static RpcProxy rpcProxy;
    public static void main(String[] args) {
        HelloService helloService1 =  rpcProxy.create(HelloService.class);
        String result1 = helloService1.hello("World");
        System.out.println(result1);

        HelloService helloService2 = rpcProxy.create(HelloService.class,"sample.hello2");
        String result2 = helloService2.hello("世界");
        System.out.println(result2);
        System.exit(0);

    }
}
