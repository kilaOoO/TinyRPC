package com.hbs.sample.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bingsenh on 2019/11/26.
 */
public class RpcBoostrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcBoostrap.class);

    public static void main(String[] args) {
        LOGGER.debug("start server");
        new ClassPathXmlApplicationContext("spring.xml");
    }

}
