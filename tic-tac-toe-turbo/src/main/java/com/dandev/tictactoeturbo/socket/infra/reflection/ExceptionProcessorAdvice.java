package com.dandev.tictactoeturbo.socket.infra.reflection;

import com.dandev.tictactoeturbo.socket.dtos.Request;
import com.dandev.tictactoeturbo.socket.infra.classes.Response;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.HandlerException;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.WebSocketControllerHandlerException;
import com.dandev.tictactoeturbo.socket.infra.reflection.exceptions.ExceptionHandlerNotFound;
import com.dandev.tictactoeturbo.socket.infra.reflection.exceptions.NoWebSocketControllerHandlerExceptionFind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class ExceptionProcessorAdvice {

    private final Logger LOGGER = Logger.getLogger(ExceptionProcessorAdvice.class.getName());

    Map<String, Object> beansWithAnnotation;

    public ExceptionProcessorAdvice(final ApplicationContext applicationContext) {
        beansWithAnnotation = applicationContext.getBeansWithAnnotation(WebSocketControllerHandlerException.class);
        LOGGER.info("Added WebSocketControllerHandlerException beans");
    }

    public Object execute(Throwable e, Request<?> request) {

        Object handlerController = beansWithAnnotation.values().stream().findFirst().orElseThrow(NoWebSocketControllerHandlerExceptionFind::new);
        for (Method declaredMethod : handlerController.getClass().getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(HandlerException.class)) {
                HandlerException handlerExceptionAnnot = declaredMethod.getAnnotation(HandlerException.class);
                Class<?> exceptionClazzAnnotation = handlerExceptionAnnot.value();
                System.out.println(exceptionClazzAnnotation);
                System.out.println(e.getClass());
                if (exceptionClazzAnnotation.equals(e.getCause().getClass())) {

                   return executeMethod(declaredMethod, handlerController, e.getCause(), request);
                }
            }
        }

        throw new ExceptionHandlerNotFound(e);
    }

    private Object executeMethod(Method method, Object object, Throwable e, Request<?> request) {
        try {
           return method.invoke(object, e, request);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException("Error when execute method "+object.getClass()+method.getName(),ex);
        }
    }


}
