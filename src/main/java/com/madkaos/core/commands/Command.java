package com.madkaos.core.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Command {
    public String name();

    public String usageKey() default "";

    public String permission() default "";

    public boolean isVIP() default false;

    public boolean onlyGame() default false;

    public Argument[] arguments() default {};

    public int minArguments() default Integer.MIN_VALUE;
}
