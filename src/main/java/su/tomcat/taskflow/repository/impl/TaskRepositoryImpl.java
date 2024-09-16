package su.tomcat.taskflow.repository.impl;

import org.springframework.stereotype.Repository;
import su.tomcat.taskflow.domain.task.TaskEntity;
import su.tomcat.taskflow.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

  @Override
  public Optional<TaskEntity> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public List<TaskEntity> findAllByUserId(Long userId) {
    return null;
  }

  @Override
  public void assignToUserById(Long taskId, Long userId) {

  }

  @Override
  public void create(TaskEntity task) {

  }

  @Override
  public void update(TaskEntity task) {

  }

  @Override
  public void delete(Long taskId) {

  }
}
