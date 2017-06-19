package com.ioc.xinchang.xutilsinject;

import android.content.Context;
import android.view.View;

import com.ioc.xinchang.xutilsinject.DynamicProxy.ListenerInvocationhandler;
import com.ioc.xinchang.xutilsinject.annotion.ContentView;
import com.ioc.xinchang.xutilsinject.annotion.EventBase;
import com.ioc.xinchang.xutilsinject.annotion.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinchang on 2017/3/3.
 */

public class InjectUtils {

    public static void inject(Context context) {
        injectLayout(context);
        inJectView(context);
        injectEvents(context);
    }



    /**
     * 注入事件
     *
     * @param context
     */
    private static void injectEvents(Context context) {
        Class<?> clazz = context.getClass();

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            Annotation[] annotions = method.getAnnotations();
            for(Annotation annotion:annotions){
                //获取注解 Onclick
                Class<?> annitonType = annotion.annotationType();
                EventBase eventbase = annitonType.getAnnotation(EventBase.class);
                if(eventbase==null){
                    continue;
                }

                String listenerSeter =  eventbase.listerSetter();
                String callBackMothod = eventbase.callBackMothod();
                Class<?> listerType = eventbase.listerType();
                //方法名与方法Method的对应关系
                Map<String, Method> methodMap = new HashMap<>();

                methodMap.put(callBackMothod,method);

                try {
                    Method valueMethod = annitonType.getDeclaredMethod("value");
                    try {
                        int[] viewIds = (int[]) valueMethod.invoke(annotion);
                        for(int viewId:viewIds){
                            Method findviewById = clazz.getMethod("findViewById",int.class);
                            View view = (View) findviewById.invoke(context,viewId);
                            if(view==null){
                                continue;
                            }
                            /**
                             * listenerSeter: setOnClickListener
                             * listerType：OnclickListener
                             */
                           Method setOnClickListener = view.getClass().getMethod(listenerSeter,listerType);
                           // ListenerInvocationHandler handler = new ListenerInvocationHandler(context,methodMap);
                            ListenerInvocationhandler handler = new ListenerInvocationhandler(context,methodMap);
                            //实现了ListenerType的接口
                            Object proxy = Proxy.newProxyInstance(listerType.getClassLoader(),new Class[]{listerType},handler);

                            setOnClickListener.invoke(view,proxy);

                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }


            }
        }

    }

    /**
     * 注入布局
     *
     * @param context
     */
    public static void injectLayout(Context context) {
        int layoutId = 0;
        Class<?> contextClass = context.getClass();
        //拿到Activity类上面的注解
        ContentView contentView = contextClass.getAnnotation(ContentView.class);
        if (contentView != null) {
            layoutId = contentView.value();
        }
        try {
            Method method = contextClass.getMethod("setContentView", int.class);
            method.invoke(context, layoutId);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 注入控件
     *
     * @param context
     */
    public static void inJectView(Context context) {
        Class<?> clazz = context.getClass();
        Field[] fileds = clazz.getDeclaredFields();
        for (Field fleld : fileds) {
            //
            ViewInject viewInject = fleld.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int valueId = viewInject.value();
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    try {
                        View view = (View) method.invoke(context, valueId);
                        fleld.setAccessible(true);
                        fleld.set(context, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
