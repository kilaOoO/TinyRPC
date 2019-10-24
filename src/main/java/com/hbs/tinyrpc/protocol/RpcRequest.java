package com.hbs.tinyrpc.protocol;

import lombok.Data;

/**
 * RPC Request
 * Created by bingsenh on 2019/10/24.
 */
@Data
public class RpcRequest {
    private String requestId;
    private String className;
    private String methodName;
    private Class<?> parameterTypes;
    private Object[] parameters;
}
