package su.tomcat.taskflow.service;

import su.tomcat.taskflow.domain.user.User;

public interface UserService {

  User getById(Long userId);

  User getByEmail(String email);

  User update(User user);

  User create(User user);

  boolean isTaskOwner(Long userId, Long taskId);

  void delete(Long userId);

}
