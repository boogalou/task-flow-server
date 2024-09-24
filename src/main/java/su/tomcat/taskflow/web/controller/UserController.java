package su.tomcat.taskflow.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import su.tomcat.taskflow.domain.task.Task;
import su.tomcat.taskflow.domain.user.User;
import su.tomcat.taskflow.service.TaskService;
import su.tomcat.taskflow.service.UserService;
import su.tomcat.taskflow.web.dto.task.TaskDto;
import su.tomcat.taskflow.web.dto.user.UserDto;
import su.tomcat.taskflow.web.mappers.TaskMapper;
import su.tomcat.taskflow.web.mappers.UserMapper;
import su.tomcat.taskflow.web.validation.OnCreate;
import su.tomcat.taskflow.web.validation.OnUpdate;

import java.util.List;

@Tag(name = "User Controller", description = "User API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
public class UserController {

  private final UserService userService;
  private final TaskService taskService;
  private final UserMapper userMapper;
  private final TaskMapper taskMapper;

  @PutMapping
  @Operation(summary = "Update user")
  public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
    User user = userMapper.toEntity(userDto);
    User updatedUser = userService.update(user);
    return userMapper.toDto(updatedUser);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get user by id")
  public UserDto getById(@PathVariable Long id) {
    User user = userService.getById(id);
    return userMapper.toDto(user);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete user by id")
  public void delete(@PathVariable Long id) {
    userService.delete(id);
  }

  @GetMapping("/{id}/tasks")
  @Operation(summary = "Get all user tasks by id")
  public List<TaskDto> getTasksByUserId(@PathVariable Long id) {
    List<Task> tasks = taskService.getAllByUserId(id);
    return taskMapper.toDto(tasks);
  }

  @PostMapping("/{id}/tasks")
  @Operation(summary = "Add task to user")
  public TaskDto create(
      @PathVariable Long id,
      @Validated(OnCreate.class)
      @RequestBody TaskDto taskDto
  ) {
    Task task = taskMapper.toEntity(taskDto);
    Task createdTask = taskService.create(task, id);
    return taskMapper.toDto(createdTask);
  }
}
