package nl.qien.passwordexample.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.qien.passwordexample.domain.User;
import nl.qien.passwordexample.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
    private UserService userService;
	
    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        userService.save(user);
    }
    
    @PostMapping("/login")
    public String login(@RequestBody final User user) {
    	return userService.login(user) ? "OK" : "NOT LOGGED IN";
    }
}