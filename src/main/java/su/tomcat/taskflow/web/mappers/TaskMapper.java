package su.tomcat.taskflow.web.mappers;

import org.mapstruct.Mapper;
import su.tomcat.taskflow.domain.task.Task;
import su.tomcat.taskflow.web.dto.task.TaskDto;
import su.tomcat.taskflow.web.dto.task.TaskResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
  TaskResponseDto toDto(Task task);

  List<TaskResponseDto> toDto(List<Task> dto);

  Task toEntity(TaskDto  dto);
}
