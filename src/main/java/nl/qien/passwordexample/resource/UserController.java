package nl.qien.passwordexample.resource;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.qien.passwordexample.annotation.LoggedIn;
import nl.qien.passwordexample.domain.User;
import nl.qien.passwordexample.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
    private UserService userService;
	
    @RequestMapping(value = "/sign-up", 
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody final User user, final Errors errors) {
		final User newUser = userService.save(user);
		URI responseURI = URI.create(request.getServerName() + ":" + request.getServerPort()+request.getRequestURI() + "/" + newUser.getId());
		return ResponseEntity.created(responseURI).build();
    }
    
    @PostMapping("/login")
    public String login(@Valid @RequestBody final User user) {
    	return userService.login(user) ? "OK" : "NOT LOGGED IN";
    }
    
    @LoggedIn
    @GetMapping("/authenticatedUser")
    public ResponseEntity<?> test() {
    	return ResponseEntity.noContent().build();
    }
    
}