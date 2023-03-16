package com.mydddyh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)         // 适用元素种类
@Retention(RetentionPolicy.RUNTIME) // 保持策略
public @interface SystemLog {
    // 描述信息
    String businessName();
    // 请求参数
    String requestArgs() default "";
    // 响应结果
    String responseResult() default "";
}
