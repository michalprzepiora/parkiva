package pl.com.przepiora.parkiva.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.com.przepiora.parkiva.model.dto.CarDTO;
import pl.com.przepiora.parkiva.service.UserService;

import javax.validation.Valid;
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
        modelAndView.addObject("newCar", new CarDTO());
        modelAndView.addAllObjects(userDataMap);
        return modelAndView;
    }

    @PostMapping("user/panel/add_car")
    public ModelAndView addCar(ModelAndView modelAndView,
                               @Valid @ModelAttribute CarDTO newCar,
                               Errors errors) {
        modelAndView.setViewName("redirect:home");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        userService.addCarToUser(userName, newCar);
        return modelAndView;

    }
}
