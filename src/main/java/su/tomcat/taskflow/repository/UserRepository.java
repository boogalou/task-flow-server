package su.tomcat.taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import su.tomcat.taskflow.domain.user.Role;
import su.tomcat.taskflow.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


  Optional<User> findByEmail(String email);

}
