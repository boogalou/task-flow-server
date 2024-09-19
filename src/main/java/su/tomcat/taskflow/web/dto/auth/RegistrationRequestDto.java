package su.tomcat.taskflow.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationRequestDto {

  @NotNull(message = "Username is Required")
  private String username;

  @NotNull(message = "Email is Required")
  private String email;

  @NotNull(message = "Password is Required")
  private String password;
}
