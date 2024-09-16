package su.tomcat.taskflow.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import su.tomcat.taskflow.domain.task.TaskEntity;
import su.tomcat.taskflow.domain.user.UserEntity;
import su.tomcat.taskflow.service.TaskService;
import su.tomcat.taskflow.service.UserService;
import su.tomcat.taskflow.web.dto.task.TaskDto;
import su.tomcat.taskflow.web.dto.task.TaskResponseDto;
import su.tomcat.taskflow.web.dto.user.UserDto;
import su.tomcat.taskflow.web.mappers.TaskMapper;
import su.tomcat.taskflow.web.mappers.UserMapper;
import su.tomcat.taskflow.web.validation.OnCreate;
import su.tomcat.taskflow.web.validation.OnUpdate;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

  private final UserService userService;
  private final TaskService taskService;
  private final UserMapper userMapper;
  private final TaskMapper taskMapper;

  @PutMapping
  public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
    UserEntity user = userMapper.toEntity(userDto);
    UserEntity updatedUser = userService.update(user);
    return userMapper.toDto(updatedUser);
  }

  @GetMapping("/{id}")
  public UserDto getById(@PathVariable Long id) {
    UserEntity user = userService.getById(id);
    return userMapper.toDto(user);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    userService.delete(id);
  }

  @GetMapping("/{id}tasks")
  public List<TaskResponseDto> getTasksByUserId(@PathVariable Long id) {
    List<TaskEntity> tasks = taskService.getAllByUserId(id);
    return taskMapper.toDto(tasks);
  }

  @PostMapping("/{id}/tasks")
  public TaskResponseDto create(
      @PathVariable Long id,
      @Validated(OnCreate.class)
      @RequestBody TaskDto taskDto
  ) {
    TaskEntity task = taskMapper.toEntity(taskDto);
    TaskEntity createdTask = taskService.create(task, id);
    return taskMapper.toDto(createdTask);
  }
}
