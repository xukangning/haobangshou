package com.hbs.common.josn.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JSON {
    String name() default "";

    boolean serialize() default true;

    boolean deserialize() default true;

    String format() default "";
}
