package nl.qien.passwordexample.aspect;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import nl.qien.passwordexample.domain.User;
import nl.qien.passwordexample.exceptions.AuthenticationException;
import nl.qien.passwordexample.service.UserService;

@Aspect
@Component
public class LoginAspect {

	@Autowired
	private HttpServletRequest context;
	
	@Autowired
	private UserService userService; 
	
	@Around("@annotation(nl.qien.passwordexample.annotation.LoggedIn)")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
		final Optional<String> authHeader = getAuthorizationHeader();
		if (authHeader.isPresent()) {
			final Optional<Map<String, String>> credentialsOptional = getUsernamePassword(authHeader.get());
			if (credentialsOptional.isPresent()) {
				if (userService.login(getUser(credentialsOptional.get()))) {
					return joinPoint.proceed();
				}
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
	
	private User getUser(final Map<String, String> credentials) {
		final User user = new User();
		user.setUsername(credentials.get("username"));
		user.setPassword(credentials.get("password"));
		return user;
	}
	
	private Optional<String> getAuthorizationHeader() {
		final Optional<String> authHeader = Optional.ofNullable(context.getHeader("Authorization"));
		if (authHeader.isPresent()) {
			return authHeader;
		}
		return Optional.empty();
	}
	
	private Optional<Map<String, String>> getUsernamePassword(final String authHeader) {
		final String[] authArray = authHeader.split(":");
		if (authArray.length == 2) {
			final Map<String, String> credentials = new HashMap<String, String>();
			credentials.put("username", authArray[0]);
			credentials.put("password", authArray[1]);
			return Optional.of(credentials);
		}
		return Optional.empty();
	}
	
}
