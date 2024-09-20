package su.tomcat.taskflow.repository;

import su.tomcat.taskflow.domain.user.Role;
import su.tomcat.taskflow.domain.user.User;

import java.util.Optional;

public interface UserRepository {

  Optional<User> findById(Long id);

  Optional<User> findByEmail(String email);

  void update(User user);

  void create(User user);

  void insertUserRole(Long userId, Role role);

  boolean isTaskOwner(Long userId, Long taskId);

  void delete(Long userId);

}
