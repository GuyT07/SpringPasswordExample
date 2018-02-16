package nl.qien.passwordexample.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@EntityListeners(UserUpdateListner.class)
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull
    @Size(min=3, message="Username should have at least 2 characters")
    private String username;
    
    @NotNull
    @Size(min=2, message="Password should have at least 2 characters")
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
