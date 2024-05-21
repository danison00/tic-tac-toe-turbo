package com.dandev.tictactoeturbo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tic-tac-toe")
public class PageController {


    @GetMapping()
    public String endPoint1() {

        return "index";
    }

    @GetMapping("**")
    public String endPoint3() {

        return "index";
    }


}
