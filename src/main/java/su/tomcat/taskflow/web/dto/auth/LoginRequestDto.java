package su.tomcat.taskflow.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {

  @NotNull(message = "Email is Required")
  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email is Invalid")
  private String email;

  @NotNull(message = "Password is Required")
  @NotBlank(message = "Password cannot be blank")
  private String password;
}
