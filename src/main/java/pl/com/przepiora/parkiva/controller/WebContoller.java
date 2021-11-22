package pl.com.przepiora.parkiva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebContoller {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/go")
    public String go() {
        return "go";
    }


}
