package pl.com.przepiora.parkiva.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {
    @GetMapping("/go")
    public String go(){
        return "go";
    }
}
