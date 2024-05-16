package com.dandev.tictactoeturbo;

import com.dandev.tictactoeturbo.socket.infra.reflection.controller.ControllersContainerMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ControllersContainerMapTest {

    @Autowired
    private ControllersContainerMap controllersContainerMap;

    @Test
    public void shoudReturnTrue() {

//        Assertions.assertThrows(RuntimeException.class, () -> controllersContainerMap.handle("/mycontroller2/action1", "passei esta mensagem"));

    }

    @Test
    public void shoudReturnTruwe() {

//        controllersContainerMap.handle("/mycontroller3/action1");

    }


}
