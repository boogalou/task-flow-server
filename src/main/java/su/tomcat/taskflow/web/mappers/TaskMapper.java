package su.tomcat.taskflow.web.mappers;

import org.mapstruct.Mapper;
import su.tomcat.taskflow.domain.task.Task;
import su.tomcat.taskflow.web.dto.task.TaskDto;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {}
