package com.hbs.tinyrpc.zookeeper;

import com.hbs.tinyrpc.registry.ServiceRegistry;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bingsenh on 2019/11/26.
 */
public class ZooKeeperServiceRegistery implements ServiceRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceRegistery.class);
    private final ZkClient zkClient;

    public ZooKeeperServiceRegistery(String zkAddress){
        // 创建 Zookeeper 客户端
        zkClient = new ZkClient(zkAddress,Constant.ZK_SESSION_TIMEOUT,Constant.ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("connect zookeeper");
    }
    @Override
    public void register(String serviceName, String serviceAddress) {
        // 创建 registry 节点（持久）
        String registerPath = Constant.ZK_REGISTERY_PATH;
        if(!zkClient.exists(registerPath)){
            zkClient.createPersistent(registerPath);
            LOGGER.debug("create registery node:{}",registerPath);
        }

        // 创建 service 节点 （持久）
        String servicePath = registerPath +"/"+serviceName;
        if(!zkClient.exists(servicePath)){
            zkClient.createPersistent(servicePath);
            LOGGER.debug("create service node: {}",servicePath);
        }

        // 创建 address 节点（临时）
        String addressPath = servicePath+"/address-";
        String addressNode = zkClient.createEphemeralSequential(addressPath,serviceAddress);
        LOGGER.debug("create address node {}",addressNode);
    }
}
