package com.dandev.tictactoeturbo.socket.infra.reflection.controller;

import com.dandev.tictactoeturbo.socket.dtos.Request;
import com.dandev.tictactoeturbo.socket.infra.classes.Response;
import com.dandev.tictactoeturbo.socket.infra.exceptions.ParamNotFound;
import com.dandev.tictactoeturbo.socket.infra.reflection.ExceptionProcessorAdvice;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.RequestBody;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.RequestParam;
import com.dandev.tictactoeturbo.socket.infra.reflection.converter.ConverterContainer;
import com.dandev.tictactoeturbo.socket.infra.reflection.exceptions.ExceptionHandlerNotFound;
import com.dandev.tictactoeturbo.util.JsonConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import static com.dandev.tictactoeturbo.socket.infra.reflection.controller.ControllersContainerMap.ControllerWrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class GatewayController {

    private final static Logger LOGGER = Logger.getLogger(GatewayController.class.getName());

    private final ControllersContainerMap controllers;
    private final JsonConverter jsonConverter;
    private final ConverterContainer converters;
    private final  ExceptionProcessorAdvice exceptionProcessorAdvice;

    public GatewayController(ApplicationContext context, ControllersContainerMap controllersContainerMap, JsonConverter jsonConverter, ConverterContainer converterContainer, final ExceptionProcessorAdvice exceptionProcessorAdvice) {
        this.controllers = controllersContainerMap;
        this.jsonConverter = jsonConverter;
        this.converters = converterContainer;
        this.exceptionProcessorAdvice = exceptionProcessorAdvice;
        LOGGER.info("GatewayController initialized");
    }

    public Optional<Object> handle(Request<?> request) {
        String endPoint = request.uri().getUri();
        Optional<ControllerWrapper> controllerOpt = controllers.getControllerMatch(endPoint, request.verb());

        if (controllerOpt.isEmpty()) {
            LOGGER.warning("No endpoint find to " + request.uri() + " " + request.verb());
            return Optional.empty();
        }

        ControllerWrapper controller = controllerOpt.get();
        Parameter[] parameters = controller.methodMatch.getParameters();

        Object[] paramsInstances = getParamsInstancesToController(parameters, request);
        try {
            Object object = controller.methodMatch.invoke(controller.instance, paramsInstances);
            if (object == null) return Optional.empty();
            return Optional.of(object);
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getCause().toString());
            Object response = null;
            try {

                response = exceptionProcessorAdvice.execute(e, request);
            } catch (ExceptionHandlerNotFound ex) {
                ex.printStackTrace();
            }
            if(response == null) return  Optional.empty();
           return  Optional.of(response);
        }


    }

    public Object[] getParamsInstancesToController(Parameter[] parameters, Request<?> request) {
        List<Object> params = new ArrayList<>();
        for (Parameter parameter : parameters) {
            Object paramInstance = null;
            if (parameter.isAnnotationPresent(RequestBody.class)) {
                paramInstance = jsonConverter.deserialize(request.body().toString(), parameter.getType());

            } else if (parameter.isAnnotationPresent(RequestParam.class)) {
                Optional<String> paramStringOpt = request.uri().getParam(parameter.getName());
                paramInstance = paramStringOpt.orElseThrow(() -> new ParamNotFound(parameter.getName()));

                if (!parameter.getType().equals(String.class)) {
                    paramInstance = converters.get(parameter.getType()).execute(paramStringOpt.get());
                }

            } else if (parameter.getAnnotations().length == 0 && parameter.getType().equals(WebSocketSession.class)) {
                paramInstance = request.body();
            }

            params.add(paramInstance);
        }
        return params.toArray();
    }



}
