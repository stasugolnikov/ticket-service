package ru.itmo.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.web.entity.UserEntity;
import ru.itmo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        UserEntity userEntity = new UserEntity();
        modelAndView.addObject("userEntity", userEntity);
        modelAndView.setViewName("/registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid UserEntity userEntity, BindingResult bindingResult) {
        if (userService.findByEmail(userEntity.getEmail()).isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (userService.findByUsername(userEntity.getUsername()).isPresent()) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        ModelAndView modelAndView = new ModelAndView();
        if (!bindingResult.hasErrors()) {
            userService.saveUser(userEntity);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new UserEntity());
        }
        modelAndView.setViewName("/registration");
        return modelAndView;
    }
}
