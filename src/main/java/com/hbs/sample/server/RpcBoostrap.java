package com.hbs.sample.server;

import com.hbs.sample.api.HelloService;
import com.hbs.tinyrpc.registry.ServiceRegistry;
import com.hbs.tinyrpc.server.RpcServer;
import com.hbs.tinyrpc.zookeeper.ZooKeeperServiceRegistery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bingsenh on 2019/11/26.
 */
public class RpcBoostrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcBoostrap.class);

    public static void main(String[] args) {
        LOGGER.info("start server");
        new ClassPathXmlApplicationContext("server-spring.xml");

    }

}
