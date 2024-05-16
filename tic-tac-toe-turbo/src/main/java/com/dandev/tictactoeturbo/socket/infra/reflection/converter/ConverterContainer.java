package com.dandev.tictactoeturbo.socket.infra.reflection.converter;

import com.dandev.tictactoeturbo.Application;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.Converter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Configuration
public class ConverterContainer {
    private static final Logger LOGGER = Logger.getLogger(ConverterContainer.class.getName());
    private final Map<Class<?>, ConverterAbstract<?>> converters = new HashMap<>();

    public ConverterContainer(ApplicationContext context){
        var converters = context.getBeansWithAnnotation(Converter.class);
        converters.forEach((name, bean)->{
            if (bean.getClass().getSuperclass().equals(ConverterAbstract.class)) {
                String nameClassAbstract = bean.getClass().getGenericSuperclass().getTypeName();
                String nameTypeGeneric = nameClassAbstract.substring(nameClassAbstract.indexOf("<")+1, nameClassAbstract.indexOf(">"));
                try {
                    Class<?> aClass = Class.forName(nameTypeGeneric);
                    this.converters.put(aClass, (ConverterAbstract<?>)bean);
                    LOGGER.info("Adding converter to "+aClass);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        });
    }

    public ConverterAbstract<?> get(Class<?> clazz){
        ConverterAbstract<?> converter = converters.get(clazz);
        if (converter == null)
            throw new RuntimeException("No converter find to from " + clazz );
        return converter;
    }
}
