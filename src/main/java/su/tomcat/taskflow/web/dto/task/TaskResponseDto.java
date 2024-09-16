package su.tomcat.taskflow.web.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDto {
  private Long id;

  private String title;
  private String description;
  private String color;
  private String category;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dueDate;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private boolean isCompleted;
}
