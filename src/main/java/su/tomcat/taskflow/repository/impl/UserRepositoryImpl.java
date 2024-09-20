package su.tomcat.taskflow.repository.impl;

import org.springframework.stereotype.Repository;
import su.tomcat.taskflow.domain.user.Role;
import su.tomcat.taskflow.domain.user.User;
import su.tomcat.taskflow.repository.UserRepository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
  @Override
  public Optional<User> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return Optional.empty();
  }

  @Override
  public void insertUserRole(Long userId, Role role) {

  }

  @Override
  public boolean isTaskOwner(Long userId, Long taskId) {
    return false;
  }

  @Override
  public void create(User user) {

  }

  @Override
  public void update(User user) {

  }

  @Override
  public void delete(Long userId) {

  }
}
