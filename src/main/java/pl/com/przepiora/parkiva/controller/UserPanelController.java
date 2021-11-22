package pl.com.przepiora.parkiva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserPanelController {
    @GetMapping("user/panel/home")
    public ModelAndView userPanelHome(ModelAndView modelAndView) {
        modelAndView.setViewName("user_panel_home");
        return modelAndView;
    }
}
