package com.ioc.xinchang.xutilsinject.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xinchang on 2017/3/3.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EventBase {
    //设置监听方法
    String listerSetter();
    //事件类型
    Class<?> listerType();
    //回调方法
    String callBackMothod();
}
