package pl.com.przepiora.parkiva.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.com.przepiora.parkiva.service.UserService;

import java.util.Map;

@Controller
public class UserPanelController {

    private UserService userService;

    public UserPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user/panel/home")
    public ModelAndView userPanelHome(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> userDataMap = userService.findUserDataByUsername(authentication.getName());
        modelAndView.setViewName("user_panel_home");
        modelAndView.addAllObjects(userDataMap);
        return modelAndView;
    }

    @GetMapping("user/panel/add_car")
    public ModelAndView addCar(ModelAndView modelAndView) {
        modelAndView.setViewName("user_panel_add_car");
        return modelAndView;

    }
}
