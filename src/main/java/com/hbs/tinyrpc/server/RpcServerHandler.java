package com.hbs.tinyrpc.server;

import com.hbs.tinyrpc.protocol.RpcRequest;
import com.hbs.tinyrpc.protocol.RpcResponse;
import com.hbs.tinyrpc.utils.StringUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by bingsenh on 2019/11/26.
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);
    private final Map<String,Object> handlerMap;
    public RpcServerHandler(Map<String,Object> handlerMap){
        this.handlerMap = handlerMap;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        // 创建并初始化 RPC 响应对象
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        try{
            Object result = handle(rpcRequest);
            rpcResponse.setResult(result);
        }catch (Exception e){
            LOGGER.error("handle result failure",e);
            rpcResponse.setException(e);
        }

        // 写入 RPC 响应对象并自动关闭连接
        channelHandlerContext.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handle(RpcRequest rpcRequest) throws InvocationTargetException {
        // 获取服务对象
        String serviceName = rpcRequest.getInterfaceName();
        String serviceVersion = rpcRequest.getServiceVersion();
        if(StringUtil.isNotEmpty(serviceVersion)){
            serviceName += "-"+serviceVersion;
        }
        System.out.println(serviceName);
        Object serviceBean = handlerMap.get(serviceName);
        if(serviceBean == null){
            throw new RuntimeException(String.format("can not find service bean by key: %s",serviceName));
        }

        // 获取反射调用所需要的参数
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName,parameterTypes);
        return serviceFastMethod.invoke(serviceBean,parameters);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("server caught exception",cause);
        ctx.close();
    }
}
