package com.dandev.tictactoeturbo.socket.infra.reflection.annotations;

import com.dandev.tictactoeturbo.util.WebSocketVerb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {
    String value() default "";
    WebSocketVerb verb = WebSocketVerb.DELETE;

}