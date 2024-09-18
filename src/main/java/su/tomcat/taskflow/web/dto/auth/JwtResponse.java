package su.tomcat.taskflow.web.dto.auth;

import lombok.Data;

@Data
public class JwtResponse {

  private String accessToken;

  private String refreshToken;
}
