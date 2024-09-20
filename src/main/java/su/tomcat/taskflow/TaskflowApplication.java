package su.tomcat.taskflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import su.tomcat.taskflow.config.DotenvConfig;

@SpringBootApplication
@EnableTransactionManagement
public class TaskflowApplication {

  public static void main(String[] args) {
    DotenvConfig.loadEnv();
    SpringApplication.run(TaskflowApplication.class, args);
  }

}
