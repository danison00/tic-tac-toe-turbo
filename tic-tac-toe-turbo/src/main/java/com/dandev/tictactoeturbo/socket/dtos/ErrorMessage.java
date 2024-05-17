package com.dandev.tictactoeturbo.socket.dtos;

import com.dandev.tictactoeturbo.socket.enums.ErrorCode;

public record ErrorMessage(ErrorCode code, String payload) {
}
