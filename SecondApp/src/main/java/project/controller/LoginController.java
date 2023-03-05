package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String showSuccessLoginPage() {return "loginSuccess";}

    @GetMapping("/loginFailure")
    public String showFailureLoginPage() {
        return "loginFailure";
    }
}
