package su.tomcat.taskflow.service;

import su.tomcat.taskflow.domain.user.UserEntity;

public interface UserService {

  UserEntity getById(Long userId);

  UserEntity getByEmail(String email);

  UserEntity update(UserEntity user);

  UserEntity create(UserEntity user);

  boolean isTaskOwner(Long userId, Long taskId);

  void delete(Long userId);

}
