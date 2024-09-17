package su.tomcat.taskflow.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvConfig {
  public static void loadEnv() {
    Dotenv dotenv = Dotenv.configure().load();

    String springDataSourceUser = dotenv.get("DB_USER");
    String springDataSourcePassword = dotenv.get("DB_PASSWORD");
    String springDataSourceUrl = dotenv.get("DB_URL");

    System.setProperty("DB_URL", springDataSourceUrl);
    System.setProperty("DB_USER", springDataSourceUser);
    System.setProperty("DB_PASSWORD", springDataSourcePassword);
  }
}
