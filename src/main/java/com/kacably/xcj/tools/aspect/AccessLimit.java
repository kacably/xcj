package com.kacably.xcj.tools.aspect;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {


    //限制的时间长度
    int timeLength();

    //限制时间长度单位
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    //限制最大访问次数
    int maxCount();

    //唯一标识
    String keyArgName() default "";

    //超限提示语
    String message() default "";

}
