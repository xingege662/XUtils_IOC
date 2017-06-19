package com.ioc.xinchang.xutilsinject.annotion;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xinchang on 2017/3/3.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventBase(listerSetter = "setOnClickListener" ,listerType = View.OnClickListener.class,callBackMothod = "onClick")
public @interface Onclick {
    //控件id设置点击事件
    int[] value() default  -1;
}
