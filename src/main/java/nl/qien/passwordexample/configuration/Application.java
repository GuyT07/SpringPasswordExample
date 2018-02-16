package nl.qien.passwordexample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import nl.qien.passwordexample.annotation.LoggedIn;
import nl.qien.passwordexample.aspect.LoginAspect;

@Configuration
@EnableAspectJAutoProxy
public class Application {

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
    public LoginAspect loginAspect() {
        return new LoginAspect();
    }
}
