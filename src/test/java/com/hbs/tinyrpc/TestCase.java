package com.hbs.tinyrpc;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by bingsenh on 2019/11/27.
 */
public class TestCase {

    private static final String connectionString = "127.0.0.1:2181";
    public static final Integer sessionTimeout = 2000;
    public static ZooKeeper zkClient = null;

    public TestCase() throws IOException {
        //三个参数分别为连接的zookeeper集群服务器的ip，超时时间，监听器
        zkClient = new ZooKeeper(connectionString, sessionTimeout, new Watcher() {
            //收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
            public void process(WatchedEvent event) {
                System.out.println(event.getType() + "," + event.getPath());
            }
        });
    }

    @Test
    public void createNode() throws Exception{
        /*
         * 传入四个参数
         * 1、创建的节点
         * 2、节点数据
         * 3、节点的权限，OPEN_ACL_UNSAFE表示内部应用权限
         * 4、节点类型，4种：持久化节点，带序列持久化节点，临时节点，带序列的临时节点
         */
        String path = zkClient.create("/idea",
                "helloworld".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(path);
    }



    @Test
    public void getData()  throws Exception{
        byte[] data = zkClient.getData("/idea", false, null);
        System.out.println(new String(data));
    }
}