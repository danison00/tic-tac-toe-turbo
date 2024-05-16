package com.dandev.tictactoeturbo.socket.infra.reflection.controller;

import com.dandev.tictactoeturbo.socket.dtos.Request;
import com.dandev.tictactoeturbo.socket.infra.classes.Response;
import com.dandev.tictactoeturbo.socket.infra.enums.ResponseStatusCode;
import com.dandev.tictactoeturbo.socket.infra.exceptions.ParamNotFound;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.RequestBody;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.RequestParam;
import com.dandev.tictactoeturbo.socket.infra.reflection.converter.ConverterContainer;
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

    public GatewayController(ApplicationContext context, ControllersContainerMap controllersContainerMap, JsonConverter jsonConverter, ConverterContainer converterContainer) {
        this.controllers = controllersContainerMap;
        this.jsonConverter = jsonConverter;
        this.converters = converterContainer;
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
        Object object = executeMethod(controller.instance, controller.methodMatch, paramsInstances);
        if (object == null) return Optional.empty();
        return Optional.of(object);

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


    private Object executeMethod(Object obj, Method method, Object... params) {
        try {
            return method.invoke(obj, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
