package su.tomcat.taskflow.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import su.tomcat.taskflow.domain.task.Task;
import su.tomcat.taskflow.service.TaskService;
import su.tomcat.taskflow.web.dto.task.TaskDto;
import su.tomcat.taskflow.web.mappers.TaskMapper;
import su.tomcat.taskflow.web.validation.OnUpdate;

@RestController()
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

  private final TaskService taskService;
  private final TaskMapper taskMapper;

  @GetMapping("/{id}")
  public TaskDto getById(@PathVariable Long id) {
    Task task = taskService.getById(id);
    return taskMapper.toDto(task);
  }


  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    taskService.delete(id);
  }

  @PutMapping
  public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
    Task task = taskMapper.toEntity(taskDto);
    Task updateTask = taskService.update(task);
    return taskMapper.toDto(updateTask);
  }
}
