package nl.qien.passwordexample.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@EntityListeners(UserUpdateListner.class)
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String username;
    
    private String password;
    
	public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(final String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(final String password) {
        this.password = password;
        return this;
    }
}
