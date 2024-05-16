package com.dandev.tictactoeturbo.socket.infra.reflection.exceptions;

import java.lang.reflect.Method;

public class MethodEndpointDuplicate extends RuntimeException{

    public MethodEndpointDuplicate(Method method1, Method method2, Class<?> clazz) {
        super("Endpoint duplicate in "+method1+" and "+method2+" into "+clazz);
    }
}
