package nl.qien.passwordexample.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import nl.qien.passwordexample.domain.User;
import nl.qien.passwordexample.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User save(final User user) {
		return userRepository.save(user);
	}
	
	public User findByUsername(final String username) {
		return userRepository.findByUsername(username);
	}

	public boolean login(User user) {
		final Optional<User> dbUser = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
		if (dbUser.isPresent()){
			return passwordEncoder.matches(user.getPassword(), dbUser.get().getPassword());
		}
		return false;
	}
}
