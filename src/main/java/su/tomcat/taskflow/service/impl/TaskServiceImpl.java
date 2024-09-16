package su.tomcat.taskflow.service.impl;

import org.springframework.stereotype.Service;
import su.tomcat.taskflow.domain.task.TaskEntity;
import su.tomcat.taskflow.service.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

  @Override
  public TaskEntity getById(Long taskId) {
    return null;
  }

  @Override
  public List<TaskEntity> getAllByUserId(Long userId) {
    return List.of();
  }

  @Override
  public TaskEntity update(TaskEntity task) {
    return null;
  }

  @Override
  public TaskEntity create(TaskEntity task, Long userId) {
    return null;
  }

  @Override
  public void delete(Long taskId) {

  }
}
