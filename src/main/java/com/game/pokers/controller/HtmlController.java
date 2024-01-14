package com.game.pokers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {

    @RequestMapping("/inde")
    public String indexPage() {
        return "index.html";
    }
}
