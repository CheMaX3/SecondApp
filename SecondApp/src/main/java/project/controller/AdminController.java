package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.dto.UserProfile;
import project.entity.Role;
import project.entity.User;
import project.service.UserService;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String userList(Model model) {
        List<User> allUsers = userService.allUsers();
        model.addAttribute("allUsers", allUsers);
        return "admin";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.GET)
    public String showUserProfile (Model model, @RequestParam Integer id) {
        UserProfile userProfile = userService.getUserProfile(id);
        List<Role> rolesList = userService.allRoles();
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("rolesList", rolesList);
        return "userProfile";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String updateUserProfile (@ModelAttribute("userProfile") UserProfile userProfile, @RequestParam Integer id) {
        userService.updateUserProfile(userProfile, id);
        return "redirect:/admin";
    }
}
