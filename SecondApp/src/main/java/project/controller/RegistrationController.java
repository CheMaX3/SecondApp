package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.entity.User;
import project.service.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String showAddUserForm(User user) {
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult result, Model model) {

        String userExistsError = userService.userExists(user);
        String passwordConfirmError = userService.passwordConfirmation(user);

        if (!userExistsError.isEmpty()) {
            ObjectError error = new ObjectError("globalError", userExistsError);
            result.addError(error);
        }

        if (!passwordConfirmError.isEmpty()) {
            ObjectError error = new ObjectError("globalError", passwordConfirmError);
            result.addError(error);
        }

        if (result.hasErrors()) {
            return "registrationPage";
        }

        userService.saveUser(user);
        model.addAttribute("users", userService.allUsers());
        return "redirect:/login";
    }
}
