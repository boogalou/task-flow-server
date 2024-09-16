package su.tomcat.taskflow.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

  @NotNull(message = "Email is Required")
  @Email(message = "Email is Invalid")
  private String email;

  @NotNull(message = "Password is Required")
  private String password;

}
