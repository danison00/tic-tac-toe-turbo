package com.dandev.tictactoeturbo.socket.infra.exceptions;

public class ParamNotFound extends RuntimeException {
    public ParamNotFound(String paramKey){
        super("Param "+paramKey+" not found into url");
    }
}
