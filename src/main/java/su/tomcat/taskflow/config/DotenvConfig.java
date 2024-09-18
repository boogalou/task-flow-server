package su.tomcat.taskflow.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

public class DotenvConfig {
  public static void loadEnv() {
    Dotenv dotenv = Dotenv.configure().load();

    for (DotenvEntry entry : dotenv.entries()) {
      System.setProperty(entry.getKey(), entry.getValue());
    }
  }
}
