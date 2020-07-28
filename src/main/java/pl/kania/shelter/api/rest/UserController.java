package pl.kania.shelter.api.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kania.shelter.api.model.User;
import pl.kania.shelter.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerNewUser(@RequestBody User user) {
        userService.registerUser(user);
    }



}
