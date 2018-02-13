package nl.qien.passwordexample.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import nl.qien.passwordexample.domain.User;
import nl.qien.passwordexample.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserServiceTests {
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	private final static String PASSWORD = "password";
	private final static String USERNAME = "username";
	private final static String HASHED_PASSWORD = "hashedPassword";
	private User user;
	private User hashedUser;
	
	@Before
	public void setup() {
		user = new User().setPassword(PASSWORD).setUsername(USERNAME);
		hashedUser = new User().setPassword(HASHED_PASSWORD).setUsername(USERNAME);
		when(userRepository.save(user)).thenReturn(hashedUser);
		when(passwordEncoder.encode(user.getPassword())).thenReturn(HASHED_PASSWORD);
		when(userRepository.findByUsername(USERNAME)).thenReturn(user);
		when(passwordEncoder.matches(PASSWORD, user.getPassword())).thenReturn(true);
	}
	
	@Test
	public void whenSaveInvokedCorrectly_thenTheUserWillBeSaved() {
		User newUser = userService.save(user);
		assertEquals(USERNAME, newUser.getUsername());
		assertEquals("hashedPassword", newUser.getPassword());
	}
	
	@Test
	public void whenUserLookupHappensWithCorrectData_thenAUserWillBeReturned() {
		User newUser = userService.findByUsername(USERNAME);
		assertEquals(USERNAME, newUser.getUsername());
		assertNotNull(newUser.getPassword());
	}
	
	@Test
	public void whenUserLookupHappensWithIncorrectData_thenNoUserWillBeReturned() {
		User newUser = userService.findByUsername("unknown");
		assertNull(newUser);
	}
	
	@Test
	public void whenLoginButUserCannotBeFound_thenFalseWillBeReturned() {
		boolean loggedIn = userService.login(user.setUsername("unknown"));
		assertFalse(loggedIn);
	}
	
	@Test
	public void whenLoginButPasswordsAreIncorrect_thenFalseWillBeReturned() {
		boolean loggedIn = userService.login(user.setPassword("incorrect"));
		assertFalse(loggedIn);
	}
	
	@Test
	public void whenLoginWithCorrectData_thenTrueWillBeReturned() {
		boolean loggedIn = userService.login(user);
		assertTrue(loggedIn);
	}
	
}
