package nl.qien.passwordexample.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import nl.qien.passwordexample.util.AutowireHelper;

public class UserUpdateListner {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
    @PreUpdate
    @PrePersist
    public void setPassword(User user) {
    	AutowireHelper.autowire(this);
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}