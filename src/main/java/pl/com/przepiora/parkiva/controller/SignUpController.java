package pl.com.przepiora.parkiva.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.com.przepiora.parkiva.mailer.TokenGenerator;
import pl.com.przepiora.parkiva.model.dto.UserDTO;
import pl.com.przepiora.parkiva.service.SignUpService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
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
        modelAndView.addObject("newUser", new UserDTO());
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView signupPost(ModelAndView modelAndView,
                                   @RequestParam("password1") String password1,
                                   @RequestParam("password2") String password2,
                                   @Valid @ModelAttribute UserDTO newUser,
                                   Errors errors) {
        if (!password1.equals(password2)) {
            errors.reject("NotSame", "Both password must be the same. ");
        }
        if (signUpService.isUsernameIsAlreadyUsed(newUser.getUsername())) {
            errors.reject("Username is taken.", newUser.getUsername() + " is already used.");
        }
        List<String> errorMessages = getErrorMessages(errors);
        if (errors.hasErrors()) {
            for (ObjectError e : errors.getAllErrors()) {
                log.warn(e.getDefaultMessage());
            }
            modelAndView.addObject("newUser", new UserDTO());
            modelAndView.addObject("errorMessages", errorMessages);
            modelAndView.setViewName("signup");
            return modelAndView;
        }
        String token = tokenGenerator.generate();
        signUpService.signUp(newUser, password1, password2, token);
        modelAndView.setViewName("redirect:login");
        return modelAndView;
    }

    @GetMapping("/confirm_mail")
    public ModelAndView confirmMail(ModelAndView modelAndView, @RequestParam("token") String token) {
        signUpService.confirmMail(token);
        modelAndView.setViewName("user_activated");
        return modelAndView;
    }

    private List<String> getErrorMessages(Errors errors) {
        return errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }

}
