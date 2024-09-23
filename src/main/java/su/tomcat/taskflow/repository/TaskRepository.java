package su.tomcat.taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import su.tomcat.taskflow.domain.task.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

  @Query(value = "SELECT * FROM tasks WHERE id = :userId", nativeQuery = true)
  List<Task> findAllByUserId(@Param("userId")Long userId);
}
