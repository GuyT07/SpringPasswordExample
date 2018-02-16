package nl.qien.passwordexample.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import nl.qien.passwordexample.exceptions.AuthenticationException;

public abstract class AbstractController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleUsernameAndPasswordNotEmptyException(final MethodArgumentNotValidException errors) {
    	return ResponseEntity.badRequest().body(getErrorMessages(errors.getBindingResult().getFieldErrors()));
    }
	
	@ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authorizationError(final MethodArgumentNotValidException errors) {
    	return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Authentication Failed"));
    }
    
    Map<String, List<String>> getErrorMessages(final List<FieldError> errors) {
    	List<String> errorMessages = new ArrayList<>();
    	errors.forEach(error -> errorMessages.add(error.getDefaultMessage()));
    	return Collections.singletonMap("errors", errorMessages); 
    }
}
