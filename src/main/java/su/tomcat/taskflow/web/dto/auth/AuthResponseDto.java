package su.tomcat.taskflow.web.dto.auth;

import lombok.Data;

@Data
public class AuthResponseDto {
  private Long id;
  private String username;
  private String email;
  private String userPic;
  private String accessToken;
}
