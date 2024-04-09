package com.xs.common.cache;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    String name() default "";

    //默认三分钟分钟
    long expire() default 3 *60 * 1000L;

}
