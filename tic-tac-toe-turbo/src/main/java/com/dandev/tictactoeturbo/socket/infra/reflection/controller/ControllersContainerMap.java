package com.dandev.tictactoeturbo.socket.infra.reflection.controller;


import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.Delete;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.Get;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.Post;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.Put;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.RequestBody;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.RequestParam;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.WebSocketController;
import com.dandev.tictactoeturbo.socket.infra.reflection.converter.ConverterContainer;
import com.dandev.tictactoeturbo.socket.infra.reflection.exceptions.MethodEndpointDuplicate;
import com.dandev.tictactoeturbo.socket.dtos.Request;
import com.dandev.tictactoeturbo.util.JsonConverter;
import com.dandev.tictactoeturbo.util.WebSocketVerb;
import lombok.ToString;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ControllersContainerMap {

    private final Logger LOGGER = Logger.getLogger(ControllersContainerMap.class.getName());

    Map<String, ControllerWrapper> controllers = new HashMap<>();
    private final ApplicationContext context;

    public ControllersContainerMap(ApplicationContext context, JsonConverter jsonConverter, ConverterContainer converterContainer) {
        this.context = context;
        _init();
    }

    private void _init() {

        Map<String, Object> webSocketControllers = context.getBeansWithAnnotation(WebSocketController.class);
        for (Map.Entry<String, Object> webSocketController : webSocketControllers.entrySet()) {

            ControllerWrapper controllerWrapper = new ControllerWrapper(webSocketController.getKey(), webSocketController.getValue());
            Class<?> controllerClass = context.getType(webSocketController.getKey());

            for (Method declaredMethod : controllerClass.getDeclaredMethods()) {
                if (declaredMethod.isAnnotationPresent(Post.class)) {
                    _addMethodInControllerWrapper(
                            WebSocketVerb.POST,
                            declaredMethod,
                            declaredMethod.getDeclaredAnnotation(Post.class).value(),
                            controllerWrapper);
                } else if (declaredMethod.isAnnotationPresent(Put.class)) {
                    _addMethodInControllerWrapper(
                            WebSocketVerb.PUT,
                            declaredMethod,
                            declaredMethod.getDeclaredAnnotation(Put.class).value(),
                            controllerWrapper);
                } else if (declaredMethod.isAnnotationPresent(Get.class)) {
                    _addMethodInControllerWrapper(
                            WebSocketVerb.GET,
                            declaredMethod,
                            declaredMethod.getDeclaredAnnotation(Get.class).value(),
                            controllerWrapper);
                } else if (declaredMethod.isAnnotationPresent(Delete.class)) {
                    _addMethodInControllerWrapper(
                            WebSocketVerb.DELETE,
                            declaredMethod,
                            declaredMethod.getDeclaredAnnotation(Delete.class).value(),
                            controllerWrapper);
                }
            }
            controllers.put(webSocketController.getKey(), controllerWrapper);
        }

        LOGGER.info("Mapping WebSocketController: " + controllers.size() + " find.");
    }

    private void _addMethodInControllerWrapper(WebSocketVerb verb, Method method, String endPointMethod, ControllerWrapper controllerWrapper) {
        if (controllerWrapper.methods.containsKey(new KeyMethod(verb, endPointMethod))) {
            throw new MethodEndpointDuplicate(
                    method,
                    controllerWrapper.methods.get(new KeyMethod(verb, endPointMethod)),
                    context.getType(controllerWrapper.uri));
        }
        controllerWrapper.methods.put(new KeyMethod(verb, endPointMethod), method);
    }


    public Optional<ControllerWrapper> getControllerMatch(String endpoint, WebSocketVerb verb) {
        for (Map.Entry<String, ControllerWrapper> entryController : controllers.entrySet()) {
            if (isMatchToController(entryController.getKey(), endpoint)) {

                String urlMethod = endpoint.replace(entryController.getKey(), "");
                Optional<Method> methodMatch = entryController.getValue().getMethodMatch(urlMethod, verb);

                if (methodMatch.isPresent()){
                    entryController.getValue().methodMatch = methodMatch.get();
                    return Optional.of(entryController.getValue());
                }
            }
        }
        return Optional.empty();
    }
    private boolean isMatchToController(String endpointController, String endpointRequest) {
        String[] pointsEndPointController = endpointController.split("/");
        String[] pointsEndPointRequest = endpointRequest.split("/");

        if (pointsEndPointRequest.length < pointsEndPointController.length) return false;
        for (int i = 0; i < Math.min(pointsEndPointController.length, pointsEndPointRequest.length); i++) {
            if (!pointsEndPointRequest[i].equals(pointsEndPointController[i])) {
                return false;
            }
        }
        return true;
    }

    @ToString
    public static class ControllerWrapper {
        Object instance;
        Map<KeyMethod, Method> methods = new HashMap<>();
        Method methodMatch;
        String uri;

        public ControllerWrapper(String uri, Object instance) {
            this.uri = uri;
            this.instance = instance;
        }

        public Optional<Method> getMethodMatch(String uriMethod, WebSocketVerb verb) {
            if (methods.containsKey(new KeyMethod(verb, uriMethod))) {
                return Optional.of(methods.get(new KeyMethod(verb, uriMethod)));
            }
            return Optional.empty();
        }
    }

    record KeyMethod(WebSocketVerb verb, String uri) {
    }

}
