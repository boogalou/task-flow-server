package su.tomcat.taskflow.repository;

import su.tomcat.taskflow.domain.task.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

  Optional<TaskEntity> findById(Long id);

  List<TaskEntity> findAllByUserId(Long userId);

  void assignToUserById(Long taskId, Long userId);

  void update(TaskEntity task);

  void create(TaskEntity task);

  void delete(Long taskId);

}
