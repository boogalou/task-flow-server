package su.tomcat.taskflow.service;

import su.tomcat.taskflow.domain.task.TaskEntity;

import java.util.List;

public interface TaskService {

  TaskEntity getById(Long taskId);

  List<TaskEntity> getAllByUserId(Long userId);

  TaskEntity update(TaskEntity task);

  TaskEntity create(TaskEntity task, Long userId);

  void delete(Long taskId);



}
