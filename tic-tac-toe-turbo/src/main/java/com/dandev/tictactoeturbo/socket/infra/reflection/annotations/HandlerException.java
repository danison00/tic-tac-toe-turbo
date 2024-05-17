package com.dandev.tictactoeturbo.socket.infra.reflection.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerException {
    Class<?> value();
}
