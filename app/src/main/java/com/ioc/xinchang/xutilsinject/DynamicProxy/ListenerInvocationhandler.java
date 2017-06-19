package com.ioc.xinchang.xutilsinject.DynamicProxy;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by xinchang on 2017/3/3.
 */

public class ListenerInvocationhandler implements InvocationHandler {
    private Context context;
    private Map<String, Method> methodMap;

    public ListenerInvocationhandler(Context context, Map<String, Method> map) {
        this.context = context;
        this.methodMap = map;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        //决定是否需要进行代理
        Method metd = methodMap.get(name);

        if (metd != null) {
            return metd.invoke(context, args);
        } else {
            return method.invoke(proxy, args);
        }
    }
}

