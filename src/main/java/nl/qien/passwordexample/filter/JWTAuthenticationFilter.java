package nl.qien.passwordexample.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class JWTAuthenticationFilter extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
	    .authorizeRequests()
	        .anyRequest().permitAll()
	        .and()
	        .csrf().disable()
	        .cors().disable();
	}
	
}
