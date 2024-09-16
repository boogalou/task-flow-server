package su.tomcat.taskflow.service.impl;

import org.springframework.stereotype.Service;
import su.tomcat.taskflow.domain.user.UserEntity;
import su.tomcat.taskflow.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Override
  public UserEntity getById(Long userId) {
    return null;
  }

  @Override
  public UserEntity getByEmail(String email) {
    return null;
  }

  @Override
  public UserEntity update(UserEntity user) {
    return null;
  }

  @Override
  public UserEntity create(UserEntity user) {
    return null;
  }

  @Override
  public void delete(Long userId) {

  }

  @Override
  public boolean isTaskOwner(Long userId, Long taskId) {
    return false;
  }
}
