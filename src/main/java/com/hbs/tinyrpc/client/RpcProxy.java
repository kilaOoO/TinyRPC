package com.hbs.tinyrpc.client;

import com.hbs.tinyrpc.protocol.RpcRequest;
import com.hbs.tinyrpc.protocol.RpcResponse;
import com.hbs.tinyrpc.registry.ServiceDiscovery;
import com.hbs.tinyrpc.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;


/**
 * RPC 代理
 * Created by bingsenh on 2019/11/21.
 */
@Component
public class RpcProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcProxy.class);

    private String serviceAddress;

    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(String serviceAddress){this.serviceAddress = serviceAddress;}

    public RpcProxy(ServiceDiscovery serviceDiscovery){this.serviceDiscovery = serviceDiscovery;}

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<?> interfaceClass) {
        return create(interfaceClass, "");
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<?> interfaceClass,final String serviceVersion){
        // 创建动态代理
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setInterfaceName(method.getDeclaringClass().getName());
                        rpcRequest.setServiceVersion(serviceVersion);
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setParameterTypes(method.getParameterTypes());
                        rpcRequest.setParameters(args);

                        // 获取 RPC 地址
                        if(serviceDiscovery !=null){
                            String serviceName = interfaceClass.getName();
                            if(StringUtil.isNotEmpty(serviceVersion)){
                                serviceName += "-" + serviceVersion;
                            }

                            serviceAddress = serviceDiscovery.discover(serviceName);
                            LOGGER.debug("discover service:{} => {}",serviceName,serviceAddress);
                        }

                        if(StringUtil.isEmpty(serviceAddress)){
                            throw new RuntimeException("server address is empty");
                        }

                        // 从 RPC 服务地址中解析主机名与端口号
                        String[] array = StringUtil.split(serviceAddress,":");
                        String host = array[0];
                        int port = Integer.parseInt(array[1]);

                        // 创建 RPC 客户端对象并发送 RPC 请求
                        RpcClient client = new RpcClient(host,port);
                        long time = System.currentTimeMillis();
                        RpcResponse rpcResponse = client.send(rpcRequest);
                        LOGGER.debug("time: {}ms",System.currentTimeMillis()-time);
                        if(rpcResponse == null){
                            throw new RuntimeException("response is null");
                        }

                        // 返回 RPC 响应结果
                        if(rpcResponse.hasExpection()){
                            throw rpcResponse.getException();
                        }else {
                            return rpcResponse.getResult();
                        }
                    }
                }
        );
    }
}
