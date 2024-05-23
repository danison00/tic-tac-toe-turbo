package com.dandev.tictactoeturbo.socket.dtos;


import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
public class Message {

    private String payload;
    private String dateHour;
    private UUID idSender;
    private UUID idReceiver;
}
