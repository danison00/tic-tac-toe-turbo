package com.dandev.tictactoeturbo;

import com.dandev.tictactoeturbo.util.URI;
import org.junit.jupiter.api.Test;

public class URITest {

    @Test
    public void test1(){
        URI uri = new URI("/danison/teste/uri?name=danison&boyfriend=joao");
        System.out.println(uri.getUri());
        System.out.println(uri.getParam("name"));
        System.out.println(uri.getParam("boyfriend"));
    }
}
