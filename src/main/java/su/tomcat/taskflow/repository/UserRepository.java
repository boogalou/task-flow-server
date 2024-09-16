package su.tomcat.taskflow.repository;

import su.tomcat.taskflow.domain.user.Role;
import su.tomcat.taskflow.domain.user.UserEntity;

import java.util.Optional;

public interface UserRepository {

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByEmail(String email);

  void update(UserEntity user);

  void create(UserEntity user);

  void insertUserRole(Long userId, Role role);

  boolean isTaskOwner(Long userId, Long taskId);

  void delete(Long userId);

}
