package com.hbs.tinyrpc.protocol;

import lombok.Data;

/**
 * RPC Response
 * Created by bingsenh on 2019/10/24.
 */
@Data
public class RpcResponse {
    private String requestId;
    //private String error;
    private Object result;
    private Exception exception;
    public boolean hasExpection(){
        return exception!=null;
    }
//    public boolean isError(){
//        return error != null;
//    }
}
