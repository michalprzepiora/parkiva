package pl.com.przepiora.parkiva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.com.przepiora.parkiva.mailer.TokenGenerator;
import pl.com.przepiora.parkiva.service.MailService;
import pl.com.przepiora.parkiva.service.SignUpService;

@Controller
public class SignUpController {

    private SignUpService signUpService;

    private TokenGenerator tokenGenerator;

    @Autowired
    public SignUpController(SignUpService signUpService, TokenGenerator tokenGenerator) {
        this.signUpService = signUpService;
        this.tokenGenerator = tokenGenerator;
    }

    @GetMapping("/signup")
    public ModelAndView signupGet(ModelAndView modelAndView) {
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView signupPost(ModelAndView modelAndView,
                                   @RequestParam("username") String username,
                                   @RequestParam("password1") String password1,
                                   @RequestParam("password2") String password2,
                                   @RequestParam("name") String name,
                                   @RequestParam("surname") String surname,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("address") String address) {
        String token = tokenGenerator.generate();
        signUpService.signUp(username, password1, password2, token, name, surname, phone, address);

        modelAndView.setViewName("redirect:login");
        return modelAndView;
    }

    @GetMapping("/confirm_mail")
    public ModelAndView confirmMail(ModelAndView modelAndView, @RequestParam("token") String token) {
        signUpService.confirmMail(token);
        modelAndView.setViewName("user_activated");
        return modelAndView;
    }

}
