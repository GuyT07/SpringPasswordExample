package nl.qien.passwordexample.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.qien.passwordexample.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
   
	User findByUsername(String username);
	
}
