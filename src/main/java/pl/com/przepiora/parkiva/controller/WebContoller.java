package pl.com.przepiora.parkiva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pl.com.przepiora.parkiva.service.SignUpService;

@Controller
public class WebContoller {

    private SignUpService signUpService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/go")
    public String go() {
        return "go";
    }


}
