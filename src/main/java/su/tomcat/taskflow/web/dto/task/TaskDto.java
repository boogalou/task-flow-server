package su.tomcat.taskflow.web.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import su.tomcat.taskflow.web.validation.OnCreate;
import su.tomcat.taskflow.web.validation.OnUpdate;

import java.time.LocalDateTime;

@Data
public class TaskDto {

  @NotNull(message = "ID must be not null", groups = OnUpdate.class)
  private Long id;

  @NotNull(message = "Title must be not null", groups = {OnCreate.class, OnUpdate.class})
  @Length(max = 255, message = "Title must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
  private String title;

  @Length(max = 255, message = "Description must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
  private String description;
  private String color;
  private String category;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dueDate;

  private boolean isCompleted;
}
