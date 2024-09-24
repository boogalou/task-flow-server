package su.tomcat.taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import su.tomcat.taskflow.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


  Optional<User> findByEmail(String email);

  @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Task t WHERE t.user.id = :userId AND t.id = :taskId")
  boolean isTaskOwner(@Param("userId") Long userId, @Param("taskId") Long taskId);


}
