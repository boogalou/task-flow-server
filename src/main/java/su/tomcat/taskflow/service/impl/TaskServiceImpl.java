package su.tomcat.taskflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.tomcat.taskflow.domain.exception.ResourceNotFoundException;
import su.tomcat.taskflow.domain.task.Task;
import su.tomcat.taskflow.domain.user.User;
import su.tomcat.taskflow.repository.TaskRepository;
import su.tomcat.taskflow.service.TaskService;
import su.tomcat.taskflow.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final UserService userService;


  @Override
  @Transactional(readOnly = true)
  public Task getById(Long taskId) {
    return taskRepository.findById(taskId)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> getAllByUserId(Long userId) {
    return taskRepository.findAllByUserId(userId);
  }

  @Override
  @Transactional
  public Task update(Task task) {
    taskRepository.save(task);
    return task;
  }

  @Override
  @Transactional
  public Task create(Task task, Long userId) {
    User user = userService.getById(userId);
    user.getTasks().add(task);
    userService.update(user);
    return task;
  }

  @Override
  @Transactional
  public void delete(Long taskId) {
    taskRepository.deleteById(taskId);
  }
}
